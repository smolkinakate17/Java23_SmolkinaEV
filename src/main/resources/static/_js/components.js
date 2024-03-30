

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
function getISODate(date) {
    return date.toISOString().substring(0, 10);
}