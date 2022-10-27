function addWish(e, context, currAmountWishItem){
    let productId  = parseInt(e.getAttribute("data-productId"));
    let url = context + `/add-wish`
    $.ajax({
        url: url,
        method: "GET",
        data: {
            'productId' : productId
        },
        async: false,
        success: function (data){
            console.log(data)
            let str = data.toString()
            let notify = str.slice(0, str.length - 2);
            if(notify === 'error'){
                document.getElementById("modal-error").classList.add('is-visible')
            }
            else if(notify === 'success'){
                document.querySelectorAll('.wish_count').forEach(c => c.innerText = (currAmountWishItem + 1).toString())
                // document.getElementById("wish_count").innerText = (currAmountWishItem + 1).toString()
                location.reload()
            }
            else if(notify === 'must-login'){
                window.location.replace(context + '/wish-list')
            }
        },
        error: function (error){

        }
    })
}
function openModal(e){
    let id = parseInt(e.getAttribute("data-id"));
    document.getElementById('link-delete').setAttribute("data-wishItemId", id.toString())
}
function deleteWishItem(e, context){
    let wishItemId  = parseInt(e.getAttribute("data-wishItemId"));
    let url = context + `/remove-wish`
    $.ajax({
        url: url,
        method: "GET",
        data: {
            'wishItemId' : wishItemId
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
                window.location.replace(context+ '/wish-list')
            }
        },
        error: function (error){

        }
    })
}