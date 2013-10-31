/******************
 * 
 *  Javascript for reverse Geocoding
 * 
 */

$(document).ready(function () {
    window.onload = loadScript;
});

//GeoCoding service, 
var geocoder;
var latlng;
var adress;

function initialize() {
    geocoder = new google.maps.Geocoder();
    var lati = $("#lat").text();
    var long = $("#lng").text();
    var lat = parseFloat(lati);
    var lng = parseFloat(long);
    lat = Math.round(lat*1000000)/1000000;
    lng = Math.round(lng*1000000)/1000000;
    latlng = new google.maps.LatLng(lat, lng);
    geocoder.geocode({'latLng': latlng}, function(results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            if (results[0]) {
                $("#adress").text(results[0].formatted_address);
            } else {
                //alert('No results found');
            }
        } else {
            //alert('Geocoder failed due to: ' + status);
        }
    });
}

function loadScript() {
    var script = document.createElement("script");
    script.type = "text/javascript";
    script.src = "http://maps.googleapis.com/maps/api/js?key=AIzaSyBVALfSa9gT-Op-AYP_0dAq27D1-udAGCs&sensor=true&callback=initialize";
    document.body.appendChild(script);
}
