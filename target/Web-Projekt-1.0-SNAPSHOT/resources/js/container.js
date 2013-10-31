/*******************************************
 * 
 *  Base class for the restful pages
 */

var container = (function() {
    
    var baseUri = "http://localhost:8080/Web-Projekt/rs/";

    return {
        getProductCatalogue : function(){
            return new ProductCatalogue(baseUri + "products");
        },
        getBaseUri : function(){
            return baseUri;
        },
        getShopCatalogue : function(lat, lng) {
            if (!lat || !lng) {
                lat = 57.7092;
                lng = 11.9732;
            }
            return new ShopCatalogue(baseUri+"shops?lat="+lat+"&long="+lng);
        }
    };
})();