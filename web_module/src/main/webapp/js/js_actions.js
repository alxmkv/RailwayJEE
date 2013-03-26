// Login/registration form validation
// TODO: - language check;
function validateForm(id){
    if(id == "login-form"){
        for(i = 1; i < 3; i++){
            var elem = document.forms[id].elements[i];
            if(!elem.value){
                alert('You haven\'t filled in ' + elem.name + '!');
                elem.focus();
                return false;
            }
        }
    }
    if(id == "register-form"){
        var password = document.forms[id]["password"].value;
        var confpsw = document.forms[id]["confirmpassword"].value;
        var email = document.forms[id]["email"].value;
        for(i = 1; i < 7; i++){
            var elem = document.forms[id].elements[i];
            if(!elem.value){
                alert('You haven\'t filled in ' + elem.name + '!');
                elem.focus();
                return false;
            }
        }
        if(confpsw != password){
            alert("Passwords are different!");
            document.forms[id]["password"].focus();
            return false;
        }
        if(!email.match(/^[^0-9][A-z0-9_]+([.][A-z0-9_]+)*[@][A-z0-9_]+([.][A-z0-9_]+)*[.][A-z]{2,4}$/i)){
            alert("Email is incorrect");
            document.forms[id]["email"].focus();
            return false;
        }
    }
    if(id == "profile-form"){
        var password = document.forms[id]["password"].value;
        var confpsw = document.forms[id]["confirmpassword"].value;
        for(i = 0; i < 6; i++){
            var elem = document.forms[id].elements[i];
            if(!elem.value){
                alert('You haven\'t filled in ' + elem.name + '!');
                elem.focus();
                return false;
            }
        }
        if(confpsw != password){
            alert("Passwords are different!");
            document.forms[id]["password"].focus();
            return false;
        }
    }
    return true;
}   // validateForm()