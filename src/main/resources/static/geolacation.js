let coordinateText = document.getElementById('coordinate_text');
let addressText = document.getElementById('address_text');
let form = document.getElementById('geolocation');

form.addEventListener('submit', async (e) => {
    e.preventDefault();
    let input = document.getElementById('geolocation_input').value;
    let response = await fetch(`./api?parameter=${input}`, {
        method: 'GET',
    });

    let result = await response.json();
    coordinateText.innerHTML = 'coordinates: ' + result.coordinates;
    addressText.innerHTML = 'address: ' + result.address;
});
