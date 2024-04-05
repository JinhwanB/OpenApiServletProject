const latElement = document.querySelector('.lat-input');
const lntElement = document.querySelector('.lnt-input');
const buttonElement = document.querySelector('.get-location');
const wifiButton = document.querySelector('.get-wifi');

// 현재 위치 좌표 가져오는 함수
function getLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(getSuccess);
    }
}

function getSuccess(position) {
    const lat = position.coords.latitude;
    const lng = position.coords.longitude;

    latElement.value = lat;
    lntElement.value = lng;
}

// 근처 와이파이 정보 가져오는 함수
function getWifi(e) {
    location.href = `?lat=${latElement.value}&lnt=${lntElement.value}`;
}

buttonElement.addEventListener('click', getLocation);
wifiButton.addEventListener('click', getWifi);