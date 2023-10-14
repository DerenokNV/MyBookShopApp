$(document).ready( function () {
  let value = document.querySelector('.ProductCard-rating');
  var count = value.id;

  var main= document.getElementById("Rating Rating_noStyleLink");
  var i = 0;
  const b = new Boolean(false);
  var type;
  while ( i < 5 ) {

    if ( i < count ) {
      type = ' <span class="Rating-star Rating-star_view"> ';
    } else {
      type = ' <span class="Rating-star"> ';
    }

    var str =  '<span class="Rating-stars"> ' + type +
                // ' <span class="Rating-star Rating-star_view"> ' +
                    ' <svg xmlns="http://www.w3.org/2000/svg" width="19" height="18" viewBox="0 0 19 18"> ' +
                       ' <g> ' +
                          ' <g> ' +
                             ' <path fill="#ffc000" d="M9.5 14.925L3.629 18l1.121-6.512L0 6.875l6.564-.95L9.5 0l2.936 5.925 6.564.95-4.75 4.613L15.371 18z"></path> ' +
                          ' </g> ' +
                       ' </g> ' +
                 ' </svg> ' +
               ' </span>';

    //main.innerHTML += str;
    main.innerHTML = main.innerHTML + str;
    i++;
  }
});
