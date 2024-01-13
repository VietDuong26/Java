const resSub = document.getElementById('res-sub')
const watchPoint = document.getElementById('watch-point')
const tablePoint = document.getElementById('table-point')
const subList = document.getElementById('sub')
const registerSubject = document.getElementById('register-subject')
const tableResSub = document.getElementById('table-res-sub')
resSub.addEventListener('click', function() {
    resSub.style.display = "none"
    watchPoint.style.display = "none"
    registerSubject.style.display = null
})
watchPoint.addEventListener('click', function() {
    watchPoint.style.display = "none"
    resSub.style.display = "none"
    tablePoint.style.display = null
})
subList.onchange = function() {
    let valueSub = this.value
    if (valueSub != "sub0") {
        tableResSub.style.display = null
    } else {
        tableResSub.style.display = "none"
    }
}
