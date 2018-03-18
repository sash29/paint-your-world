    pchosencolor = "#FFA07A";
    rmid = "lr-1" ;
    imgurClientId="b8a2fb986a82c5f";

    function enableLC(){
      var bgimg = new Image();
      bgimg.src = "/images/" + rmid + ".jpg";

      var lc = LC.init(document.getElementById('roompaintlc'),
        {
                  backgroundShapes: [
                    LC.createShape('Image', {x: 2, y: 2, image: bgimg, scale: 1.4})
                  ]}
        );
      console.log(lc);


//    <!-- custom UI example... -->
      var tools = [
        {
          name: 'brush',
          el: document.getElementById('tool-pencil'),
          tool: new LC.tools.Pencil(lc)
        },
        {
          name: 'rectangle',
          el: document.getElementById('tool-rectangle'),
          tool: new LC.tools.Rectangle(lc)
        },
        {
          name: 'line',
          el: document.getElementById('tool-line'),
          tool: new LC.tools.Line(lc)
        },
        {
          name: 'polygon',
          el: document.getElementById('tool-polygon'),
          tool: new LC.tools.Polygon(lc)
        },        
        {
          name: 'eraser',
          el: document.getElementById('tool-eraser'),
          tool: new LC.tools.Eraser(lc)
        }
      ];

      var activateTool = function(t) {
        lc.setTool(t.tool);
        lc.setColor('primary',$("#chosencolor").css("backgroundColor"));
        if(t.name== 'polygon' || t.name== 'rectangle'){
          lc.setColor('secondary',$("#chosencolor").css("backgroundColor"));
        }

        tools.forEach(function(t2) {
          if (t == t2) {
            t2.el.style.backgroundColor = 'yellow';
          } else {
            t2.el.style.backgroundColor = 'transparent';
          }
        });
      }

      tools.forEach(function(t) {
        t.el.style.cursor = "pointer";
        t.el.onclick = function(e) {
          e.preventDefault();
          activateTool(t);
        };
      });
      activateTool(tools[0]);
      return lc;
    };

    function geturlparameters(sparam){
      var spurl = window.location.search.substring(1);
      console.log(spurl)
      var surlvars = spurl.split('&');
      for (var i=0;i<surlvars.length;i++){
        var sparmname  = surlvars[i].split('=');
        if(sparmname[0] ==sparam)
          {return sparmname[1];}
      }
    }

    function uploadtoImgur(){
      console.log("uploadtoImgur");
      
    //  $('[data-action=upload-to-imgur]').click(function(e) {
     
      $('#uploadtoimgur').click(function(e){
      console.log("uploading..");
      e.preventDefault();

      $('.imgur-submit').html('Uploading...')

      // this is all standard Imgur API; only LC-specific thing is the image
      // data argument;
      $.ajax({
        url: 'https://api.imgur.com/3/image',
        type: 'POST',
        headers: {
          // Your application gets an imgurClientId from Imgur
          Authorization: 'Client-ID ' + imgurClientId,
          Accept: 'application/json'
        },
        data: {
          // convert the image data to base64
          image:  lc.canvasForExport().toDataURL().split(',')[1],
          type: 'base64'
        },
        success: function(result) {
          var url = 'https://imgur.com/gallery/' + result.data.id;
          $("#imgururl").text(url);
          $('.imgur-submit').html("<a href='" + url + "'>" + url + "</a>");
          // now save the room to the DB
          console.log("on sucesss");
          $("#txtimgururl").val(url);
          console.log("submit to paintroomform");
          $("#paintroomform").submit();
         },
      });
    });
    
    }

    $(function(){
       // rmid = geturlparameters('rmid');
       // pchosencolor = '#' + geturlparameters('selcolor');
       if($("#txtcolor").val()!=""){
          pchosencolor= "#" + $("#txtcolor").val();
       }
       if($("#txtroom").val()!=""){
          rmid=$("#txtroom").val();
       }
       
       console.log(pchosencolor);
       console.log(rmid);
       $("#chosencolor").css("backgroundColor",pchosencolor);
       lc = enableLC();
       uploadtoImgur(lc);
    });
