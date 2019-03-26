
(function($){
	$.fn.datesAreInvalid = function(){
		var introduced = $("#introduced");
		var discontinued = $("#discontinued");
		return introduced.val() && 
					discontinued.val() && 
					(new Date(introduced.val())).getTime() >= (new Date(discontinued.val())).getTime();
	}
}(jQuery));

// On load hide feedback icons.
(function($){
	$(".glyphicon").css("visibility", "hidden");
	$("small").css("visibility", "hidden");
}(jQuery));


(function($) {
	$.fn.addFeedback = function(elt, isError){
		if(!elt.is(".has-feedback")){
			elt.addClass("has-feedback");
		}
		if(isError){
			elt.removeClass("has-success");
			elt.addClass("has-error");
			elt.find("span.glyphicon-remove").css("visibility", "visible");
			elt.find("span.glyphicon-ok").css("visibility", "hidden");
		} else{
			elt.addClass("has-success");
			elt.removeClass("has-error");
			elt.find("span.glyphicon-remove").css("visibility", "hidden");
			elt.find("span.glyphicon-ok").css("visibility", "visible");
		}
	}
}(jQuery));

(function($) {
	$("#addComputer").on("submit", function(event){
		var input = $("#computerName");
		if(input.val() === ""){ // Check the name.
			event.preventDefault();
			event.stopPropagation();
			$.fn.addFeedback(input.parent(), true);
			input.parent().find("small").css("visibility", "visible");
		}
		if($.fn.datesAreInvalid()){
			event.preventDefault();
			event.stopPropagation();
			$.fn.addFeedback($("#discontinued").parent(), true);
			$.fn.addFeedback($("#introduced").parent(), true);
			$("#discontinued").parent().find("small").css("visibility", "visible");

		}
	});
}(jQuery));

(function($) {
	$("#computerName").change(function(event){
		input = $("#computerName");
		$.fn.addFeedback(input.parent(), input.val() === "");
		if(input.val() !== ""){
			input.parent().find("small").css("visibility", "hidden");
		}
	});
}(jQuery));

(function($){
	$("#introduced").change(function(event){
		if($.fn.datesAreInvalid()){
			$.fn.addFeedback($("#discontinued").parent(), true);
			$.fn.addFeedback($("#introduced").parent(), true);
			$("#discontinued").parent().find("small").css("visibility", "visible");
		}
		else{
			$.fn.addFeedback($("#discontinued").parent(), false);
			$.fn.addFeedback($("#introduced").parent(), false);
			$("#discontinued").parent().find("small").css("visibility", "hidden");

		}
	});
}(jQuery));


(function($){
	$("#discontinued").change(function(event){
		if($.fn.datesAreInvalid()){
			$.fn.addFeedback($("#discontinued").parent(), true);
			$.fn.addFeedback($("#introduced").parent(), true);
			$("#discontinued").parent().find("small").css("visibility", "visible");
		}
		else{
			$.fn.addFeedback($("#discontinued").parent(), false);
			$.fn.addFeedback($("#introduced").parent(), false);
			$("#discontinued").parent().find("small").css("visibility", "hidden");
		}
	});
}(jQuery));
