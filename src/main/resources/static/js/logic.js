// Display user infor when no infor
const inforUser = document.getElementById('js-infor-user');
if (inforUser.innerText == '') {
    inforUser.style.display = "none";
    console.log("delete infor user");
}

const logout_btn=document.getElementById("logout_bt");
    logout_btn.addEventListener('click',function (){
    window.location='/logout';
})

// Display logout
const userBtn = document.querySelector('.link-user')
const logoutBtn = document.querySelector('.logout-area')
const fullWeb = document.querySelector('.full-web')
function displayLogout(){
    logoutBtn.style.display = "flex";
    console.log('displayLogout')
}
function hideLogout(){
    if (logoutBtn.style.display == "flex") {logoutBtn.style.display = null; console.log('hideLogout')
    logoutBtn.addEventListener('click', function (event) {
        event.stopPropagation()
    })
}
}
userBtn.addEventListener('click', displayLogout)
userBtn.addEventListener('click', function (event) {
    event.stopPropagation()
})
fullWeb.addEventListener('click', hideLogout)


const tableItems = document.getElementsByClassName('mgn-content')
for (let tableItem of tableItems) {
    if (tableItem.innerText === 'F') {
        tableItem.style.color = 'red'; tableItem.style.fontWeight = 'bold'
    }}