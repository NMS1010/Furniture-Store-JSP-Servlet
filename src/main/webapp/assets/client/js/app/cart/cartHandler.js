function openCartItemModal(e){
    let id = parseInt(e.getAttribute("data-cartItemId"));
    document.getElementById('link-delete').setAttribute("data-cartItemId", id.toString())
}
function addCartItem(e, context){
    let productId  = parseInt(e.getAttribute("data-productId"));
    let url = context + `/cart/add`
    $.ajax({
        url: url,
        method: "GET",
        data: {
            'productId' : productId,
            'quantity' : 1
        },
        async: false,
        success: function (data){
            console.log(data)
            let str = data.toString()
            let notify = str.slice(0, str.length - 2);
            if(notify === 'error'){
                document.getElementById("modal-error").classList.add('is-visible')
            }
            else if(notify.includes('success')){
                document.querySelectorAll('.cart_item_count').forEach(c => c.innerText = (parseInt(notify)).toString())
            }
            else if(notify === 'must-login'){
                window.location.replace(context + '/cart/items')
            }
        },
        error: function (error){

        }
    })
}
function deleteCartItem(e, context){
    let cartItemId  = parseInt(e.getAttribute("data-cartItemId"));
    let url = context + `/cart/remove`
    $.ajax({
        url: url,
        method: "GET",
        data: {
            'cartItemId' : cartItemId
        },
        async: false,
        success: function (data){
            console.log(data)
            let str = data.toString()
            let notify = str.slice(0, str.length - 2);
            if(notify === 'error'){
                document.getElementById("modal-error").classList.add('is-visible')
            }
            else{
                window.location.replace(context+ '/cart/items')
            }
        },
        error: function (error){

        }
    })
}
function updateCartItemQuantity(e, context){
    let cartItemId  = parseInt(e.getAttribute("data-cartItemId"));
    let quantity = parseInt(document.getElementById("cart-item-quantity-"+cartItemId.toString()).value)
    if(e.type === 'button'){
        if(e.value.includes('Increase')){
            quantity += 1
        }else{
            quantity -= 1
        }
    }
    if(quantity < 0 || quantity === 0) {
        quantity = 0
        document.getElementById("modal-delete-cart").classList.add('is-visible')
        openCartItemModal(document.getElementById('cart-remove-'+cartItemId))
        return;
    }
    let url = context + `/cart/update`
    $.ajax({
        url: url,
        method: "GET",
        data: {
            'cartItemId' : cartItemId,
            'quantity':quantity
        },
        async: false,
        success: function (data){
            console.log(data)
            let str = data.toString()
            let notify = str.slice(0, str.length - 2);
            if(notify === 'error'){
                document.getElementById("modal-error").classList.add('is-visible')
            }
            else{
                if(notify.includes('over')){
                    document.getElementById("over-quantity-" + cartItemId.toString()).innerText = "Số sản phẩm không đủ"
                    quantity = parseInt(notify)
                    document.getElementById("cart-item-quantity-" + cartItemId.toString()).value = quantity
                }else{
                    document.getElementById("over-quantity-" + cartItemId.toString()).innerText = ""
                }
                let unitPrice = parseFloat(document.getElementById("cart-item-" + cartItemId.toString() + "-unitPrice").innerText)
                let totalPrice = unitPrice * quantity;
                document.getElementById("cart-item-" + cartItemId.toString() + "-totalPrice").innerText = totalPrice.toFixed(2).toString()

                let total = 0;
                document.querySelectorAll('.total-price').forEach(c => total += parseFloat(c.innerText))
                document.getElementById("total-item-price").innerText = total.toFixed(2).toString() + " VND"
            }
        },
        error: function (error){

        }
    })
}