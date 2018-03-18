    function createcolorfamily(dmobj){

    	for(var i = 0, len = pricol_ar.length; i < len; i++){
    		//$("#"+dmobj).append("<a href='#'' id='clr-" + col_ar[i].id +"' data-role='button' data-mini='true'>" + col_ar[i].title +"</a>");
            $("#"+dmobj).append("<button id='clr-" + pricol_ar[i].id +"' class='colorselector'>" + pricol_ar[i].title +"</button>");

    	}
    }

	function shadeColor2(color, percent) {   
    	var f=parseInt(color.slice(1),16),t=percent<0?0:255,p=percent<0?percent*-1:percent,R=f>>16,G=f>>8&0x00FF,B=f&0x0000FF;
    	return "#"+(0x1000000+(Math.round((t-R)*p)+R)*0x10000+(Math.round((t-G)*p)+G)*0x100+(Math.round((t-B)*p)+B)).toString(16).slice(1);
	}

    function createcolorshades(dmobj,colcode,shadename){
    	for(var i=-3; i <= 4;i++){
    		colc = shadeColor2(colcode,i*.1)
    		var $nobj = $("<img class='colbox tooltip' style='background-color:" + colc + "' data-tooltip-content='#tooltip_content' ></img>");
    	    $nobj.data("shades",{"shadename" :shadename , "percent":Math.round((i*.1) * 100),"clrhexcode":colc});
    	    $nobj.click(function(){
    	    	shname =$(this).data("shades").shadename;
    	    	shcolor=$(this).data("shades").clrhexcode;
    	    	shtxt = "<p>Chosen:<br>" + shname + "<br>" + shcolor + "</p>";
    	    	$("#chosencolor").html(shtxt);
    	    	$("#chosencolor").css("background-color",shcolor );
    	    	$("#chosencolor").data("clrshade",shcolor);
    	    });
    	    $("#" + dmobj).append($nobj);

    	} 
    }

    function createcolorboxes(colfam){
    	var colarr = [];
    	if(colfam == "red"){colarr = col_reds;};
    	if(colfam == "orange"){colarr = col_orange;}
    	if(colfam == "yellow"){colarr = col_yellow;}
    	if(colfam == "green"){colarr = col_green;}
    	if(colfam == "blue"){colarr = col_blue;}
   	    if(colfam == "purple"){colarr = col_purple;}
   	    if(colfam == "neutral"){colarr = col_neutral;}
    	$("#div_colframe").empty();
    	console.log('creating colboxes ' + colfam);

    	for(var i=0;i< colarr.length;i++){
    	    $("#div_colframe").append("<div id='colbox-" + colarr[i].id + "'" + " class='colboxshades'></div>");
    	   
    	    createcolorshades("colbox-" + colarr[i].id,colarr[i].hexcode,colarr[i].name);
    	}
    	
    }


    function addcolboxhover(){
		$(".colbox").hover(function(){
			clr= $(this).css("background-color");
			shname=$(this).data("shades").shadename;
			shpercent = $(this).data("shades").percent;
			if(Math.sign(shpercent)>0){
				shptext =Math.abs(shpercent) + "% lighter";
			};
			if(Math.sign(shpercent)<0){
				shptext = Math.abs(shpercent) + "% darker";
			};
			if(Math.sign(shpercent)==0){
				shptext =" ";
			};

			shcode = $(this).data("shades").clrhexcode;

			//$("#div_colframe").css("background-color",clr);
			//console.log($(this).data("shades").shadename);
			clrhtml = "<p><strong>" + shname +"</strong><br>"+shptext + "<br>"+shcode + "</p>";
			$("#colorname").html(clrhtml);
			$("#colorname").css("background-color",clr);
			$("#colorname").data("clrshade",shcode);
		});
	}

	function dummysubmit(){
		selroom = $(".thumbnail.ui-selected").attr('id');
		selcolor = $("#chosencolor").data("clrshade");

		
        url="paintroom.html?rmid=" + selroom + "&selcolor=" + selcolor.slice(1);
        $("#selroom").val(selroom);
        $("#selcolor").val(selcolor.slice(1));
        console.log($("#selroom").val()) ;
        $("#submitform").submit();
        

	}