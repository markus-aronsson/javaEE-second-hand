/*******************************************
 * 
 * Javascript for the products.html file
 * 
 */

function getURLParameter(name) {
     return decodeURI(
          (RegExp(name + '=' + '(.+?)(&|$)').exec(location.search)||[,null])[1]
     );
}

//Global variables
var cl     = new ContentLoader(container.getProductCatalogue(), getURLParameter("id"));
var filter = function(i, el) {
        return true;
};

$(document).ready(function () { 

/******************************* Load the page ********************************/

    
    $.get("/Web-Projekt/sharedContent/menu.html", function(data) {
          $('#container').prepend(data, addHeader());
    });
    
    function addHeader() {
        $.get("/Web-Projekt/sharedContent/header.html", function(data){
            $('#container').prepend(data);
        });
    }

    $.get("/Web-Projekt/sharedContent/footer.html", function(data){
          $('#container').append(data);
    });
    
    loadProducts();
   
/*****************************  Page listeners and actions ********************/
   
    /** 
     * Clicking on an image should open a dialog with information about the 
     * product.
     */
    $(document).on('click','.image',function(e) {
        var id = $(this).attr('id');
        createProductWindow(id);
    });
    
    /**
     * Pressing the update button should update the filter and erase the loaded
     * products, after which new products will be loaded using the filter.
     */
    $(document).on('click','#side #sideContainer .updateBtn', function(e) {
        var inputs = $(this).parent().find("input");
        var price  = parseInt(inputs.get(0).value);
        
        $('#products').html('');
        
        filter = function(i, el) {
            return parseInt($(el).attr("price")) <= price;
        };
        
        cl.reset();
        loadProducts();
    });
    
    /**
     * When the browser window have scrolled down to the bottom of the page
     * new products should be loaded.
     */
    $(window).scroll(function() {
        if (($(window).innerHeight() + $(window).scrollTop()) >= $("#products").height() && 
            $("#products").length > 0) {
                    loadProducts();
        }
    });
   
   /**************************** Functions ************************************/
   
   function loadProducts() {
        var success = function(result) {
            $.each(result, function(i, product) {
                loadProduct(product);
            });
        };

        var fail = function() {
            alert("Can't list!!");
        };

        cl.loadF(filter, success, fail);
    }
   
   /* Load one product to the page */
   function loadProduct(product) {
       var frame = $('<div id="frame">' + 
                       '<img class="image"' + 
                         ' id="' + product.getAttribute("id") + 
                         '" src="' + product.getAttribute("imageURI") + '" />' + 
                       '<br />' +
                       '<div id="prodText"><p>' + 
                         'Name: ' + product.getAttribute("name")  + 
                         '<br />' +
                         'Price: ' + product.getAttribute("price") + 'kr' + 
                       '</p></div>' + 
                    '</div>');
                            
       frame.appendTo("#products");
   };
   
    /**
     * Create the dialog for a product
     * displaying its image and information
     * to the user
     */
    function createProductWindow(productId) {
        var price;
        var imgURI;
        var name;  
        
        cl.loadS(productId).then(function(result) {
              var products = $(result.getElementsByTagName("product"));
              var product  = products[0];
              price        = product.getAttribute("price");
              name         = product.getAttribute("name");
              imgURI       = product.getAttribute("imageURI");        
         }).then(function (){
             $("#dialog").append("<p>Product information</p>");
             $("#dialog").append("<p>Id: "    + productId + "<br />" + 
                                    "Name: "  + name      + "<br />" +
                                    "Price: " + price     + "</p>");
             $("#dialog").append("<img src='" + imgURI + "'/>");
             $('#dialog').dialog('option', 'title', 'Information about ' + name);
         });
         
         $("#dialog").css('background-color', '#EBCFA5');
         $("#dialog").dialog({
               modal:     true,
               resizable: false, 
               draggable: false, 
               show:      'fade',
               hide:      'fade',
               height:    500, 
               width:     500,
               stack:     true,
                
               buttons: {
                  Ok: function() {
                       $("#dialog").empty();
                       $(this).dialog("close");
                  }
               }
          });
    };
});
