

function setLoading(spinner, isLoading) {
    spinner.hidden = !isLoading;
}
  
function setAlert(alert, message) {
    if (!message) {
      alert.hidden = true;
      return;
    }
    alert.innerText = message;
    alert.hiddent = false;
}