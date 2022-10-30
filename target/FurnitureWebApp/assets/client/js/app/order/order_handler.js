function onApplyDiscount(context){
    let discountCode = document.getElementById("discount").value
    let discountValidateMessage = document.getElementById("discountValidateMessage")
    let discountValue = document.getElementById("discountValue")

    let url = context + '/discount/apply'
    $.ajax({
        url: url,
        method: "GET",
        data: {
            'discountCode' : discountCode
        },
        async: false,
        success: function (data){
            console.log(data)
            let str = data.toString()
            let notify = str.slice(0, str.length - 2);
            if(notify === 'error'){
                discountValidateMessage.innerText = "Mã khuyến mãi không hợp lệ"
                discountValue.value = "0 %"
            }
            else if(notify === 'expired'){
                discountValidateMessage.innerText = "Mã khuyến mãi đã hêt hạn sử dụng"
                discountValue.value = "0 %"
            }
            else{
                let discount = JSON.parse(data)
                discountValue.value = "-" + discount.discountValue.toString() + " %"
                let elem = document.getElementById("totalPrice")
                let prevPrice = parseFloat(elem.value)
                document.getElementById("discountId").value = discount.discountId.toString()
                elem.value = (prevPrice - prevPrice * parseFloat(discount.discountValue)).toString()
            }
        },
        error: function (error){

        }
    })
}