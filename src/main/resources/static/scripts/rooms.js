    var clientId = "b8a2fb986a82c5f";

    function loadimgurimg(){
      console.log("loadimgurimg");

      for(var i=0;i<rms.length;i++){
         console.log("in load");
         imgurl = rms[i].imgururl;
         imgid = imgurl.substring(imgurl.lastIndexOf("/") + 1, imgurl.length);
         console.log(imgid);
         gurl = "https://api.imgur.com/3/image/" + imgid;

         $.ajax({
           url: gurl,
           type:"GET",
           headers: {
             Authorization: 'Client-ID ' + clientId,
             Accept: 'application/json'
           },
           success: function(result){
             console.log(result.data);
             link = result.data.link;
             console.log(link);
             $("#rooms").append("<img class='roomtb' src=" + link.replace(".png","s.png") + "></img>");
          },
        });

       }
  }
  $(function(){
      loadimgurimg();
  });
