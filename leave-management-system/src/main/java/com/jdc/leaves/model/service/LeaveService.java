package com.jdc.leaves.model.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.leaves.model.dto.input.LeaveForm;
import com.jdc.leaves.model.dto.output.LeaveListVO;
import com.jdc.leaves.model.dto.output.LeaveSummaryVO;

@Service
public class LeaveService {
	
	private NamedParameterJdbcTemplate template;
	
	private static final String LEAVES_COUNT_SQL = """
			select count(leaves_student_id) 
			from leaves_day 
			where leave_date = :target 
			and leaves_classes_id = :classId
			""";
	
	private static final String SELECT_PROJECTION = """
			select distinct l.apply_date applyDate, l.classes_id classId, l.student_id studentId, sa.name student,
			s.phone studentPhone, c.teacher_id teacherId, ta.name teacher,
			l.start_date startDate, l.days, l.reason,c.start_date classStart, c.description classInfo
			from leaves l 
			join classes c on c.id = l.classes_id 
			join teacher t on t.id = c.teacher_id
			join account ta on ta.id = t.id
			join student s on s.id = l.student_id
			join account sa on sa.id = s.id
			join leaves_day ld on ld.leaves_apply_date = l.apply_date 
			and ld.leaves_classes_id = l.classes_id
			and ld.leaves_student_id = l.student_id
			""";
	@Autowired
	private ClassService classService;
	
	private SimpleJdbcInsert leaveInsert;
	private SimpleJdbcInsert leaveDaysInsert;
	
	public LeaveService(DataSource dataSource) {
		template = new NamedParameterJdbcTemplate(dataSource);
		
		leaveInsert = new SimpleJdbcInsert(dataSource);
		leaveInsert.setTableName("leaves");
		
		leaveDaysInsert = new SimpleJdbcInsert(dataSource);
		leaveDaysInsert.setTableName("leaves_day");
		
	}

	public List<LeaveListVO> search(Optional<Integer> classId, Optional<LocalDate> from, Optional<LocalDate> to) {
		
		var sb = new StringBuffer(SELECT_PROJECTION);
		sb.append("where 1 = 1 ");
		var params = new HashMap<String, Object>();
		
		var authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication.isAuthenticated() && authentication.getAuthorities().contains(authority("ROLE_Student"))) {
			if(authentication instanceof UsernamePasswordAuthenticationToken token) {
				sb.append(" and sa.email = :login");
				params.put("login", token.getName());
			}
		}
		
		sb.append(classId
						.filter(id -> id != null && id > 0)
						 .map(a-> {
							 params.put("classId", a);
							 return " and l.classes_id = :classId";
						 }).orElse("")
				);
		
		sb.append(from
				.filter(date -> date != null)
				 .map(d-> {
					 params.put("from", Date.valueOf(d));
					 return " and l.start_date >= :from";
				 }).orElse("")
		);
		
		sb.append(to
				.filter(date -> date != null)
				 .map(d-> {
					 params.put("to", Date.valueOf(d));
					 return " and l.start_date <= :to";
				 }).orElse("")
		);
		sb.append(" order by l.start_date, l.apply_date, sa.name");
		return template.query(sb.toString(), params,
				new BeanPropertyRowMapper<LeaveListVO>(LeaveListVO.class));
	}
	
	private GrantedAuthority authority(String role) {
		return AuthorityUtils.commaSeparatedStringToAuthorityList(role).get(0);
	}

	public LeaveForm findById(int studentId) {
		var sql ="""
				select classes_id classId, student_id student, apply_date applyDate, days, reason 
				from leaves_db.leaves where student_id = :studentId
				""";
		return template.queryForObject(sql, Map.of("studentId",studentId), new BeanPropertyRowMapper<LeaveForm>(LeaveForm.class));
	}

	@Transactional
	public void save(LeaveForm form) {
		// Insert into leaves table
		leaveInsert.execute(form.leavesInsertParams());

		// Insert into leaves_days table
		for (var param : form.leavesDaysInsertParams()) {
			leaveDaysInsert.execute(param);
		}

	}

	public List<LeaveSummaryVO> searchSummary(Optional<LocalDate> target) {

		var classes = classService.search(Optional.ofNullable(null), Optional.ofNullable(null),
				Optional.ofNullable(null));

		var result = classes.stream().map(LeaveSummaryVO::new).toList();

		for (LeaveSummaryVO vo : result) {
			vo.setLeaves(findLeavesForClass(vo.getClassId(), target.orElse(LocalDate.now())));
		}

		return result;
	}

	private long findLeavesForClass(int classId, LocalDate target) {
		return template.queryForObject(LEAVES_COUNT_SQL, Map.of("target", target, "classId", classId), Long.class);
	}
}