//On load
$(function() {
    
    // Hide feedback icons and messages.
    $(".glyphicon").hide();
	$("small").hide();

});

// Define function that shows the appropriate feedback.
(function($) {
	$.fn.feedback = function(elt, isError){
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

// Determine if two dates are chronologically consistent
(function($){
	$.fn.areChronological = function(first, second){
		console.log()
		return !first.val() || 
					!second.val() ||
					(new Date(first.val())).getTime() < (new Date(second.val())).getTime();
	}
}(jQuery));

// Edit form submit
(function($){
	$(".editForm").submit(function(e){		
		var name = $("#name");
		var discontinued = $("#discontinued");
		var introduced = $("#introduced");
		
		name.val(name.val().trim());

		if(name.val() === "" || !$.fn.areChronological(introduced, discontinued)){
			e.preventDefault();
			e.stopPropagation();
		}	
	});
}(jQuery));

//Listen to name changes
(function($){
	$("input[name=name]").change(function(e){
		
		var input = $("#name");
		input.val(input.val().trim());
		
		$.fn.feedback(input.parent(), input.val() === "");
		if(input.val() !== ""){
			input.parent().find("small").hide();
		}
		else{
			input.parent().find("small").show();
		}
	});
}(jQuery));

//Listen to introduction dates changes 
(function($){
	$("input[name=introduced]").change(function(event){
		var discontinued = $("#discontinued");
		var introduced = $("#introduced");
		if($.fn.areChronological(introduced, discontinued)){
			$.fn.feedback(discontinued.parent(), false);
			$.fn.feedback(introduced.parent(), false);
			discontinued.parent().find("small").hide();
			introduced.parent().find("small").hide();
		}
		else{
			$.fn.feedback(discontinued.parent(), true);
			$.fn.feedback(introduced.parent(), true);
			discontinued.parent().find("small").show();
		}
	});
}(jQuery));

//Listen to discontinued dates changes
(function($){
	$("input[name=discontinued]").change(function(event){
		var discontinued = $("#discontinued");
		var introduced = $("#introduced");
		
		if($.fn.areChronological(introduced, discontinued)){
			$.fn.feedback(discontinued.parent(), false);
			$.fn.feedback(introdduced.parent(), false);
			discontinued.parent().find("small").hide();
		}
		else{
			$.fn.feedback(discontinued.parent(), true);
			$.fn.feedback(introduced.parent(), true);
			discontinued.parent().find("small").show();
		}
	});
}(jQuery));
