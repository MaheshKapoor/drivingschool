$(document).ready( function() {
        // alert($('#search_box').val());
        var startIndex = 1;
        var count =0;
        var total= 0;
        var backbutton = 0;
        var src = null;
        var countDisplay = "";
        var currentStartIndex =0;
        $('#searchResult').hide();
        if(startIndex ==1){
            $('#btnnext').hide();
        }
        if(backbutton == 0){
            $('#btnback').hide();
        }
        $('#btnSearch, #btnnext').click(function () {

            search(startIndex);
        });
        $('#btnback').click(function () {
            search(backbutton);
        });
        function search(startIndex1) {
            $.ajax({
                type: "GET",
                url: "/drivingschool",
                data: {suburb: $('#search_box').val(), startIndex: startIndex1},
                dataType: "json",
                timeout: 60000,
                cache: false,
                success: function (data) {
                    //alert("Success" );
                    var trHTML = '';
                    console.log(data);
                    startIndex = data.queries.nextPage[0].startIndex;
                    count= data.queries.nextPage[0].count;
                    currentResultIndex = startIndex - count;
                    currentResultMaxIndex=startIndex-1;
                    backbutton = data.queries.request[0].startIndex - count;
                    total = data.queries.nextPage[0].totalResults;
                    countDisplay="Showing "+currentResultIndex +" - "+currentResultMaxIndex +" of "+ total + " Results"
                    if(startIndex >10 && startIndex < total){
                        $('#btnnext').show();
                    }else{
                        $('#btnnext').hide();
                    }
                    if(backbutton > 0){
                        $('#btnback').show();
                    }else{
                        $('#btnback').hide();
                    }
                    console.log("Start Page : "+ startIndex +
                        // " Current Page :" + startIndex -count +
                        " Back Page : " + backbutton +
                        " Total Page : " + total);
                    $.each(data.items, function (i, item) {


                        // $.each(this, function (i, item) {
                        console.log(i);
                        if (item.pagemap != null && item.pagemap.cse_thumbnail != null && item.pagemap.cse_thumbnail[0].src != null) {
                            src = item.pagemap.cse_thumbnail[0].src;
                        } else {
                            //src = "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcRw6lyiAM_cHeC7p04bRD-6Jy08QMpt_Wuvj4JZsMOPibj93Zk3dJUGn-2l";
                        }
                        trHTML += '<li class="listing hp">' +
                            '<div class="business" data-ls-trc="lv_result_listing">' +
                            '<div class="marker"><span class="mm"></span>' +
                            '</div>' +
                            '<div class="details"><h3><a href="'+item.link+'">' +item.title + '</a></h3>' +
                            '<h4 class="slogan" ><label>About us: </label>' + item.snippet +'</h4></div>' +
                            '<div class="media"><div class="logo"><a ><img class="thumbnail" src="' + src + '"></a></div></div></div></li>'
                    });
                    //});
                    $('#searchResult').html(trHTML);
                    $('#countHeading').html(countDisplay);
                    $('#searchResult').show();
                    $('#countHeading').show();
                    $('#result').show();
                },
                error: function (xhr, data) {
                    if (data.status != "SUCCESS") {
                        alert("ERROR");
                    }
                },
            });
        }
    });