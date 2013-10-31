/***********************
 * 
 * QUnit tests for the javascript
 * 
 */

var cl = new ContentLoader(container.getProductCatalogue());
var shops = new container.getShopCatalogue();

$(document).ready(function () { 

////////////////////////////////////////////////////////////////////////////////
//                                                                            //
//                               Test reset                                   //
//                                                                            //
////////////////////////////////////////////////////////////////////////////////

    test( "Test Reset", function() {
       cl.reset();
           
       ok (cl.loaded === 0 && cl.current === 0, "Passed.."); 
    });


////////////////////////////////////////////////////////////////////////////////
//                                                                            //
//                               Test load                                    //
//                                                                            //
////////////////////////////////////////////////////////////////////////////////

    // Check that the load function loads the correct amount of items,
    // depending on the number of items available.
    asyncTest( "Test Load", function() {
        var d = $.Deferred();
        var c = 0;
        cl.cat.getCount().done(
            function(count) {
                c = parseInt($(count).find("value").text());
        }).then(cl.load(
            function(prods) {
                d.resolve(prods);
            }, 
            function() {
                d.resolve(0);
        }));
        
        $.when(d.promise()).done(function(range) {
            if (range === 0) {
                ok (c === 0, "Passed!");
                start();
            } else {
                var ps = $(range).find("product");
                ok (ps.length >= 0 && ps.length <= cl.min, "Passed!");
                start();
            }
            
        });
    });

////////////////////////////////////////////////////////////////////////////////
//                                                                            //
//                           Test filtered load                               //
//                                                                            //
////////////////////////////////////////////////////////////////////////////////

    asyncTest( "Test Filtered Load", function() {
        var d = $.Deferred();
        var c = 0;
        var n = 10;
        
        cl.cat.getCount().done(
            function(count) {
                c = parseInt($(count).find("value").text());
        }).then(cl.loadF(
            function(i, el) {
                 return parseInt($(el).attr("price")) >= n;
            },
            function(prods) {
                var ok = true;
                $.each(prods, function(p) {
                    if (parseInt($(p).attr("price")) < n) {
                        ok = false;
                    }
                });
                d.resolve(ok);
            }, 
            function() {
                d.resolve(0);
        }));
        
        $.when(d.promise()).done(function(range) {
            if (range === 0) {
                ok (c === 0, "Passed!");
                start();
            } else {
                ok (range, "Passed!");
                start();
            }
            
        });
    });
    
    // Test REST API:s in shopCatalogue and rroductCatalogue
    // NB! Must execute AddDemoContent.java on an empty database as REST-API is read-only.
    asyncTest( "Test size of shopCatalogue.getAll() [ADD AddDemoContent]", function() {
        var deferred = shops.getAll();
        deferred.done(function(xml){
            var n_shops = $(xml.getElementsByTagName("shop")).size();
            ok(n_shops === 4, "Counting to 4. OK.");
            start();
        });
        deferred.fail(function() {
            ok(false, "Test failed. Service down?");
            start();
        });
    });
    
    asyncTest("Test fields of shopCatalogue.getAll() [ADD AddDemoContent]", function() {
        var deferred = shops.getAll();
        deferred.done(function(xmlResponse) {
            $.each(xmlResponse.getElementsByTagName("shop"), function(i, shop) {
                var id   = shop.getAttribute("id");
                var name = shop.getAttribute("name");
                var lat  = shop.getAttribute("lat");
                var long = shop.getAttribute("long");
                var desc = shop.getAttribute("description");
                ok((/^\d+$/g).test(id), "Id is a number. Ok.");
                ok((/^[\w\d\s]+$/i).test(name), "Name is chars, integers and whitespaces. Ok.");
                ok((/^\d+\.\d+$/g).test(lat), "Latitude is a float.");
                ok((/^\d+\.\d+$/g).test(long), "Longitude is a float.");
                ok((/^[\w\W]+$/i).test(desc), "Desc is chars, integers and whitespaces. Ok.");
            });
            start();
        });
    });
});




