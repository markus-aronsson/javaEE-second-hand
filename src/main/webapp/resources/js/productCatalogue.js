/*******************************************
 * 
 *  A proxy representing the productcatalogue
 *  for a certain shop
 *  
 */

var ProductCatalogue = function(catalogue) {
    this.catalogue = catalogue;
    this.shop      = 0;
};

ProductCatalogue.prototype = (function (){ 
        
    return { 
        
        /**
         * Find the product with the given id in the 
         * catalogue for the shop
         */
        find: function(id) {
            return $.get(this.catalogue + "/" + this.shop + "/" + id);
        },
        
        /**
         * Get all products for the shop
         */
        getAll: function(){
            return $.get(this.catalogue + "/" + this.shop);
        },
                
        /**
         * Get all products between the specified range
         * for this shop
         */
        getRange: function (fst, max) {
            return $.ajax( {
                type: "GET",
                url: this.catalogue + "/" + this.shop + "/range",
                data: "fst="+fst+"&max="+max
            });
        },
            
        /**
         * Get the number of products for this shop
         */
        getCount: function () {
             return $.ajax({
                 type: "GET",
                 url: this.catalogue + "/" + this.shop + "/count"
             });
        },
        
        /**
         * Set the id of the shop
         */
        setId: function(id) {
            this.shop = id;
        }
    };    
} ());




