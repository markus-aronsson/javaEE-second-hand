/*******************************************
 * 
 * Google Maps is used for the map.
 * Mark the user's current position,
 * and all the shops.
 * 
 */

var sstores = container.getShopCatalogue();

function initialize() {
    
    var mapOptions = {
        zoom: 12,
        // Bootstrap location to central station Gothenburg. Move if/when getting geolocation.
        center: new google.maps.LatLng(57.7092, 11.9732),
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    
    var map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);
    
    // Try HTML5 geolocation
    if(navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            var pos = new google.maps.LatLng(position.coords.latitude,
                                             position.coords.longitude);
            sstores = container.getShopCatalogue(pos.lat(), pos.lng());
            
            //Add a marker for current position:
            var myloc = new google.maps.Marker({
                clickable: false,
                icon: new google.maps.MarkerImage('//maps.gstatic.com/mapfiles/mobile/mobileimgs2.png'
                                                  , new google.maps.Size(22,22)
                                                  , new google.maps.Point(0,18)
                                                  , new google.maps.Point(11,11)),
                shadow: null,
                zIndex: 999,
                map: map
            });
            
            myloc.setPosition(pos);
            map.setCenter(pos);
            
        }, function() {}, {maximumAge:60000, timeout:5000, enableHighAccuracy:false});
    }  
    
    //Insert all shops:
    sstores.getAll().then(function(result){
        $.each($(result.getElementsByTagName("shop")), function(i, shops) {
            var id      = shops.getAttribute("id");
            var name    = shops.getAttribute("name");
            var lat     = shops.getAttribute("lat");
            var long    = shops.getAttribute("long");
            var desc    = shops.getAttribute("description");
            var latlong = new google.maps.LatLng(lat, long); 
            var contentString = '<a href="http://localhost:8080/Web-Projekt/requestBased/shop?shop=' + id +  
                                   '"><h1 id="shopViewTitle">' + name + "</h1></a> " + desc;
            
            var infowindow = new google.maps.InfoWindow({
                content: contentString
            });
            
            var storeMarker = new google.maps.Marker({
                position: latlong,
                map: map,
                title: name
            });
            
            google.maps.event.addListener(storeMarker, 'click', function() {
                infowindow.open(map, storeMarker);
            });
        });
    });
}

function loadScript() {
    var script = document.createElement("script");
    script.type = "text/javascript";
    script.src = "http://maps.googleapis.com/maps/api/js?key=AIzaSyBVALfSa9gT-Op-AYP_0dAq27D1-udAGCs&sensor=true&callback=initialize";
    document.body.appendChild(script);
}

$(document).ready(function () { 
    window.onload = loadScript;
});
