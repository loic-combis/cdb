//On load
$(function() {
    // Default: hide edit mode
    $(".editMode").hide();
    
    // Click on "selectall" box
    $("#selectall").click(function () {
        $('.cb').prop('checked', this.checked);
    });

    // Click on a checkbox
    $(".cb").click(function() {
        if ($(".cb").length == $(".cb:checked").length) {
            $("#selectall").prop("checked", true);
        } else {
            $("#selectall").prop("checked", false);
        }
        if ($(".cb:checked").length != 0) {
            $("#deleteSelected").enable();
        } else {
            $("#deleteSelected").disable();
        }
    });
    
    // Hide feddback icons and messages.
    $(".glyphicon").hide();
	$("small").hide();

});


// Function setCheckboxValues
(function ( $ ) {

    $.fn.setCheckboxValues = function(formFieldName, checkboxFieldName) {

        var str = $('.' + checkboxFieldName + ':checked').map(function() {
            return this.value;
        }).get().join();
        
        $(this).attr('value',str);
        
        return this;
    };

}( jQuery ));

// Function toggleEditMode
(function ( $ ) {

    $.fn.toggleEditMode = function() {
        if($(".editMode").is(":visible")) {
            $(".editMode").hide();
            $(".viewMode").show();
            $("#editComputer").text("Edit");
        }
        else {
            $(".editMode").show();
            $(".viewMode").hide();
            $("#editComputer").text("View");
        }
        return this;
    };

}( jQuery ));


// Function delete selected: Asks for confirmation to delete selected computers, then submits it to the deleteForm
(function ( $ ) {
    $.fn.deleteSelected = function() {
        if (confirm("Are you sure you want to delete the selected computers?")) { 
            $('#deleteForm input[name=selection]').setCheckboxValues('selection','cb');
            $('#deleteForm').submit();
        }
    };
}( jQuery ));



//Event handling
//Onkeydown
$(document).keydown(function(e) {

    switch (e.keyCode) {
        //DEL key
        case 46:
            if($(".editMode").is(":visible") && $(".cb:checked").length != 0) {
                $.fn.deleteSelected();
            }   
            break;
        //E key (CTRL+E will switch to edit mode)
        case 69:
            if(e.ctrlKey) {
                $.fn.toggleEditMode();
            }
            break;
    }
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
		const id = this.id.split("-")[1];
		
		var name = $("#name-" + id);
		var discontinued = $("#discontinued-" + id);
		var introduced = $("#introduced-" + id);
		
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
		const id = this.id.split("-")[1];
		
		var input = $("#name-" + id);
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
		const id = this.id.split("-")[1];
		var discontinued = $("#discontinued-" + id);
		var introduced = $("#introduced-" + id);
		if($.fn.areChronological(introduced, discontinued)){
			$.fn.feedback(discontinued.parent(), false);
			$.fn.feedback(introduced.parent(), false);
			discontinued.parent().find("small").hide();
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
		const id = this.id.split("-")[1];
		var discontinued = $("#discontinued-" + id);
		var introduced = $("#introduced-" + id);
		
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
