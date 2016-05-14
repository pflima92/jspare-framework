//     jspare-sdk.js 1.0.03
//     http://jspare.org/framework
//     (c) 2016 Jspare.org
//     jspare-sdk may be freely distributed under the APACHE 2 License.

(function() {

	// Baseline setup
	// --------------

	// Establish the root object, `window` in the browser, or `exports` on the
	// server.
	var root = this;

	// Create a safe reference to the Underscore object for use below.
	var jspare = function(obj) {
		if (obj instanceof jspare)
			return obj;
		if (!(this instanceof jspare))
			return new jspare(obj);
		this._wrapped = obj;
	};

	// Export the jspare object for **Node.js**, with
	// backwards-compatibility for the old `require()` API. If we're in
	// the browser, add `jspare` as a global object.
	if (typeof exports !== 'undefined') {
		if (typeof module !== 'undefined' && module.exports) {
			exports = module.exports = jspare;
		}
		exports.jspare = jspare;
	} else {
		root.jspare = jspare;
	}

	// Current version.
	jspare.version = '1.0.0';

	var _defaultCommandOptions = {

	};
	
	function Yield(yieldData) {

		for(k in yieldData){
				
			this[k] = yieldData[k];
		}
	}
	
	function request(id, parameters, headers, entity, callback){
		
		function hasCommandSupport() {

			return typeof XMLHttpRequest != 'undefined';
		}
		
		function encodeParameters(data) {
		    return Object.keys(data).map(function(key) {
		        return [key, data[key]].map(encodeURIComponent).join("=");
		    }).join("&");
		}   

		function buildUrl(id, parameters) {
			
			var url = '/' + id;
			
			if(Object.keys(parameters).length){
				url += "?";
				url += encodeParameters(parameters);
			}
			return url;
		}

		function buildRequestEntity(req) {

			return (typeof req === 'string' || req instanceof String) ? req
					: JSON.stringify(req);
		}
		
		function buildResponseEntity(resp){
			try {
				return JSON.parse(resp);
			} catch (e) {
				return resp;
			}
		}

		if (!hasCommandSupport()) {

			XMLHttpRequest = require('xmlhttprequest').XMLHttpRequest;
		}

		var cReq = new XMLHttpRequest();

		cReq.open('POST', buildUrl(id, parameters), true);
		cReq.setRequestHeader('Content-type', 'application/json; charset=utf-8');
		cReq.setRequestHeader('Accept', 'application/json; charset=utf-8');
		
		if(headers)
			for(h in headers) cReq.setRequestHeader(h, headers[h]);

		var command = this;
		command.callback = callback;
		
		cReq.onload = function() {
			
			if(this.status === 500){
				
				callback(new Error(this.statusText));
				return;
			}

			var data = {

				command : command,
				result : {
					status : this.status,
					entity : buildResponseEntity(this.responseText)
				}
			};
			if (callback)
				callback(null, data);
		};
		cReq.onerror = function(err) {

			if (callback)
				callback(err, null, this);
		};
		cReq.send(buildRequestEntity(entity));
	}
	
	Yield.prototype.resume = function(){
		
		var headers = {
				'hd-tid-tkn' : this.tid
		}
		request.bind(this, this.command.id, this.command.parameters, headers, this, this.command.callback)();
	}

	function Command(id, options) {

		this.id = id;
		this.req = {};
		this.parameters = {};
		this.yieldBinds = {};
		this.data = {};
		this.options = options || _defaultCommandOptions;
	}

	Command.prototype.options = function(options) {

		this.options = options;
		return this;
	}

	Command.prototype.request = function(req) {

		this.req = req;
		return this;
	}

	Command.prototype.addParameter = function(key, value) {

		this.parameters[key] = value;
		return this;
	}

	Command.prototype.removeParameter = function(key) {

		delete this.parameters[key];
		return this;
	}

	Command.prototype.parameters = function(parameters) {

		this.parameters = parameters;
		return this;
	}
	
	Command.prototype.onYield = function(bind, callback){
		
		//TODO add unique arg
		this.yieldBinds[bind] = callback;
		return this;
	}
	
	Command.prototype.execute = function(callback) {
		
		request.bind(this, this.id, this.parameters, {}, this.req, function(err, data){
			
			if(err){
				if (callback)
					callback(err, null);
				return;
			}
			
			//On Yield
			if(data.result.status === 206){
				
				if(!Object.keys(data.command.yieldBinds).length){
					
					throw new Error('None bind for yield registered');
				}
				
				var yieldData = data.result.entity;
				yieldData.command = data.command;
				
				data.command.yieldBinds[yieldData.bind](new Yield(yieldData));
				return;
			}

			if (callback)
				callback(null, data.result.entity);
		})();
	}
	
	Command.prototype.call = this.execute;

	// Command Interface with jspare Framework
	jspare.command = function(id) {

		return new Command(id);
	}

	// AMD registration happens at the end for compatibility with AMD loaders
	// that may not enforce next-turn semantics on modules. Even though general
	// practice for AMD registration is to be anonymous, underscore registers
	// as a named module because, like jQuery, it is a base library that is
	// popular enough to be bundled in a third party lib, but not be part of
	// an AMD load request. Those cases could generate an error when an
	// anonymous define() is called outside of a loader request.
	if (typeof define === 'function' && define.amd) {
		define('jspare', [], function() {
			return jspare;
		});
	}
}.call(this));