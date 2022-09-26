document.addEventListener('DOMContentLoaded', () => {
	document.querySelector(".sign-out").addEventListener('click', () => {
		document.querySelector(".sign-out-form").submit();
		console.log("Finished signout.");
	});
});