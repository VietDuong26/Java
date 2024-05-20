const checkStds = document.getElementsByClassName('choose-std')
const deleteBtn = document.getElementById('delete-btn')
let a = 0
for (let checkStd of checkStds) {
    checkStd.addEventListener('change', (event) => {
        if (event.currentTarget.checked) {
            a += 1
            console.log(a)
        } else {
            a -= 1
            console.log(a)
        }
        if (a >= 1) {
            deleteBtn.style.display = null
        } else {
            deleteBtn.style.display = 'none'
        }
    })
    
}

const subList = document.getElementById('sub')
const classList = document.getElementById('class')
const mainCenter = document.getElementById('main-center')
subList.onchange = function() {
    let valueSub = this.value
    if (valueSub != "sub0") {
        classList.style.display = null
    } else {
        classList.value = "class0"
        console.log(classList.value)
        mainCenter.style.display = "none"
    }
}

classList.onchange = function() {
    let valueClass = this.value
    console.log(valueClass)
    if (valueClass != "class0") {
        mainCenter.style.display = null
    } if (valueClass == "class0") {
        mainCenter.style.display = "none"
    }
}