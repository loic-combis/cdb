// On load hide feedback icons.
(function($){
	$(".glyphicon").hide();
	$("small").hide();
}(jQuery));


(function($) {
	$.fn.addFeedback = function(elt, isError){
		if(!elt.is(".has-feedback")){
			elt.addClass("has-feedback");
		}
		if(isError){
			elt.removeClass("has-success");
			elt.addClass("has-error");
			elt.find("span.glyphicon-remove").show();
			elt.find("span.glyphicon-ok").hide();
		} else{
			elt.addClass("has-success");
			elt.removeClass("has-error");
			elt.find("span.glyphicon-remove").hide();
			elt.find("span.glyphicon-ok").show();
		}
	}
}(jQuery));

(function($) {
	$("#createUser").on("submit", function(event){
		var input = $("#createUser input[name='login']");
		input.val(input.val().trim());
		if(input.val() === ""){ // Check the name.
			event.preventDefault();
			event.stopPropagation();
			$.fn.addFeedback(input.parent(), true);
			input.parent().find("small").show();
		}
		var password = $("#createUser input[name='password']");
		var confirmation = $("#createUser input[name='confirmation']");
		if(password.val().length < 6){
			event.preventDefault();
			event.stopPropagation();
			$.fn.addFeedback(password.parent(), true);
			password.parent().find("small").show();
		}
		if(password.val() !== confirmation.val()){
			event.preventDefault();
			event.stopPropagation();
			$.fn.addFeedback(confirmation.parent(), true);
			confirmation.parent().find("small").show();
		}
	});
}(jQuery));

(function($) {
	$("#createUser input[name='login']").change(function(event){
		var input = $("#createUser input[name='login']");
		input.val(input.val().trim());
		$.fn.addFeedback(input.parent(), input.val() === "");
		if(input.val() !== ""){
			input.parent().find("small").hide();
		}
		else{
			input.parent().find("small").show();
		}
	});
}(jQuery));

(function($) {
	$("#createUser input[name='password']").change(function(event){
		var input = $("#createUser input[name='password']");
		var confirmation = $("#createUser input[name='confirmation']");

		$.fn.addFeedback(input.parent(), input.val().length < 6);
		if(input.val().length >= 6){
			input.parent().find("small").hide();
		}
		else{
			input.parent().find("small").show();
		}
		
		$.fn.addFeedback(confirmation.parent(), confirmation.val() !== input.val());
		if(confirmation.val() === input.val()){
			confirmation.parent().find("small").hide();
		}
		else{
			confirmation.parent().find("small").show();
		}
		
	});
}(jQuery));

(function($) {
	$("#createUser input[name='confirmation']").change(function(event){
		var input = $("#createUser input[name='confirmation']");
		var password = $("#createUser input[name='password']");
		$.fn.addFeedback(input.parent(), input.val() !== password.val());
		if(input.val() === password.val()){
			input.parent().find("small").hide();
		}
		else{
			input.parent().find("small").show();
		}
	});
}(jQuery));