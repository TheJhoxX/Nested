window.onload = init;
var formulario;

function init(){
    formulario = document.getElementById('formulario');
    formulario.addEventListener('submit',validate);
}

function validate(e) {
    var password = document.getElementById("pswd");
    var confirmPassword = document.getElementById("cpswd");
    console.log(password.value);
    console.log(confirmPassword.value);
    if (password.value !== confirmPassword.value) {
        e.preventDefault();
        alert("Passwords do not match.");
        return false;
    }
    return true;
}

