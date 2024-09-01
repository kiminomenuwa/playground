// map.js

function initMap() {
    // HTML에 지도를 표시할 요소를 가져옵니다.
    var mapDiv = document.getElementById('map');

    // 지도 객체를 생성합니다.
    var map = new google.maps.Map(mapDiv, {
        center: {lat: -34.397, lng: 150.644},  // 기본 중심 좌표 설정
        zoom: 8
    });

    // 사용자의 현재 위치를 가져옵니다.
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            var pos = {
                lat: position.coords.latitude,
                lng: position.coords.longitude
            };

            // 현재 위치에 마커를 표시합니다.
            var marker = new google.maps.Marker({
                position: pos,
                map: map
            });

            // 지도의 중심을 현재 위치로 이동합니다.
            map.setCenter(pos);
        }, function() {
            handleLocationError(true, map.getCenter());
        });
    } else {
        // 브라우저가 Geolocation을 지원하지 않는 경우
        handleLocationError(false, map.getCenter());
    }
}

function handleLocationError(browserHasGeolocation, pos) {
    var infoWindow = new google.maps.InfoWindow;
    infoWindow.setPosition(pos);
    infoWindow.setContent(browserHasGeolocation ?
        'Error: The Geolocation service failed.' :
        'Error: Your browser doesn\'t support geolocation.');
    infoWindow.open(map);
}
