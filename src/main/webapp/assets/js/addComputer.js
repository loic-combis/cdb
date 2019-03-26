(function($) {
	$("#addComputer").on("submit", function(event){
		var input = $("#addComputer input[name=name]")[0];
		console.log(input.value);
		if(!input.value){
			event.preventDefault();
			event.stopPropagation();
		}
	});
}(jQuery));