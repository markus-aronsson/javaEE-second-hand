/*******************************************
 * 
 *  Loading more products to display in products.html
 * 
 */

var ContentLoader = function(cat, id) {
    this.cat = cat;
    cat.setId(id);
    
    this.current = 0;
    this.amount  = 3;
    
    this.loaded  = 0;
    this.min     = 6;
};

ContentLoader.prototype = (function() {

    return {
        
        reset: function() {
            this.current = 0;
            this.loaded  = 0;
        },
        
        /**
         *  Load "amount" more products, if available,
         *  else load the rest of the products that
         *  are left
         */
        load: function(success, fail) {
            var scope = this;
            
            scope.cat.getCount().then(
                function(count) {
                    var c = parseInt( $(count).find("value").text() );
                    
                    if ( (scope.current + scope.amount) < c ) {
                        return scope.amount;
                    } else if ( scope.current !== c ) {
                        return c - scope.current;
                    } else {
                        return -1;
                    }
                }, fail
            ).then(
                function(available) {
                    if ( available === -1 ) {
                        return -1;
                    } else {
                        return scope.cat.getRange(scope.current, available).then(
                            function(result) {
                                scope.current += available;
                                return result;
                            }, fail
                        ).then( success, fail );
                    }
                }, fail
            );         
        },
        
        /**
         *  Load "amount" more products, but 
         *  also filter them according to the 
         *  given filter
         */
        loadF: function(f, success, fail) {
            var scope = this;
            var items = [];
            
            var loadRange = function() {
                var dfd = $.Deferred();
                
                scope.cat.getCount().then(
                        
                    // First the amount of available products is fethced
                    function(count) {
                        var c = parseInt($(count).find("value").text());
                        if ((scope.current + scope.amount) < c) {
                            return scope.amount;
                        } else if (scope.current !== c) {
                            return c - scope.current;
                        } else {
                            return -1;
                        }
                    }, fail).then(
                    
                    // Then the service is queried for those products
                    function(available) {
                        if (available > 0) {
                            scope.cat.getRange(scope.current, available).then(
                                function(result) {
                                    scope.current += available;
                                    dfd.resolve(result);
                                },
                                function(result) {
                                    dfd.resolve(null);
                                });
                        } else {
                            dfd.resolve(-1);
                        }
                    }, fail);
                
                return dfd.promise();
            }
            
            var loadWhile = function() {
                var done = false;
                
                $.when(loadRange()).done(function(range) {
                    
                    // If we already loaded all possible products: exit
                    if (range === -1) {
                        success(items);
                        
                    // Otherwise we try and load as many as possible
                    } else {
                        var temp  = 0
                        var prods = $(range).find("product");
                        var filtr = prods.filter(f);

                        $.each(filtr, function(i, product) {
                            items.push(product);
                            temp++;

                            // Ensure we have loaded the desired amount and 
                            // atleast the minimum amount of items.
                            if ( (items.length >= scope.amount) && (scope.loaded >= scope.min || items.length >= scope.min) ) {
                                scope.current -= prods.length - temp;
                                scope.loaded  += items.length;
                                done           = true;
                                return false;
                            }
                        });

                        // If we have all products needed, call success
                        if (done) {
                            success(items);
                            
                        // Otherwise, recursively load more
                        } else {
                            loadWhile();
                        }
                    }
                });
            }
            
            // Start the loading cycle
            loadWhile();
        },
        
        /**
         * Loads a single items from the catalogue. This method should be used
         * instead of a direct access to the catlogues find, since here we can
         * be certain the catalogue is loaded with the correct shop id
         * 
         * @param {type} id - Id of product to load
         * @returns {deferred} - Deferred object of ajax request
         */
        loadS: function(id) {
            return this.cat.find(id);
        },
    };
}());
