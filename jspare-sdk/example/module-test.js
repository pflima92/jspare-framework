var jSpare = require('./../src/jspare-sdk.js');

var car = {
	nome : 'Punto',
	valor : 1000
};

jSpare.command('salvarCarro').request(car).execute(console.info);