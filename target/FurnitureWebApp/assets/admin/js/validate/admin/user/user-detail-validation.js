let options = [];
let $boxes = $('input[name=roleCheckBox]:checked')
$boxes.each(s => {
    if(s.value !== undefined)
        options.push(s.value)
});

$( '.dropdown-menu a' ).on( 'click', function( event ) {

    var $target = $( event.currentTarget ),
        val = $target.attr( 'data-value' ),
        $inp = $target.find( 'input' ),
        idx;

    if ( ( idx = options.indexOf( val ) ) > -1 ) {
        options.splice( idx, 1 );
        setTimeout( function() { $inp.prop( 'checked', false ) }, 0);
    } else {
        options.push( val );
        setTimeout( function() { $inp.prop( 'checked', true ) }, 0);
    }

    $( event.target ).blur();

    console.log( options );
    return false;
});

function validateForm() {
    let roleEmpty = $('#roleEmpty')
    console.log(options)
    if(options.length === 0){
        roleEmpty.html('Vui lòng chọn role').css('color', 'red');
        return false
    }else {
        roleEmpty.html('')
    }
    return true
}