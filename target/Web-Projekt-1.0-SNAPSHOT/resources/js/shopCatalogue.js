/*******************************************
 * 
 *  A proxy representing the shopcatalogue
 *  
 */
var ShopCatalogue = function(shops) {
    this.shops = shops;
};

ShopCatalogue.prototype = (function() {
    return {
        
        /**
         * Return all shops
         */
        getAll: function(){
            return $.get(this.shops);
        }
    };
}());