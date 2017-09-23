/* PARAMETERS FOR SEARCH BEHAVIOR
 ------------------------------------------------------------*/
var isMobile = false; //This is set to true for screens smaller than 768px;

$(document).ready(function(){
    /* TRUNCATING AWARD TEXT */

    //truncateText();

    /* SEARCH
     -------------------------------------------------------------- */
    /* ADDING PLACEHOLDER TEXT IN IE9 AND 10 -------*/
    $('input, textarea').placeholder();

    /* INITIALIZE SEARCH (ON PAGE LOAD) --------- */
    var searchfield  = $(".search-field"),
        $companyNameOption = searchfield.closest("form").find(".search-opt");
    $("#clear_all_mode").hide();
    searchfield.val("");

    searchfield
        .on("focus", function(e) {
            $companyNameOption.slideDown();
            e.stopImmediatePropagation();
        })
        /*.on("blur", function() {
         setTimeout(function() {
         if ( $("#search-comp").is(":checked") || searchfield.val().length > 0 )  {
         $companyNameOption.show();
         } else {
         $companyNameOption.slideUp();
         }
         }, 400, $companyNameOption, searchfield);
         }) */
        .on("keyup", function() {
            var $clearSearchIcon = $(".clear-search");
            $clearSearchIcon.show();
            if ( $(this).val().length == 0 ) {
                $clearSearchIcon.hide();
            }
        });

    $("#search-comp").on("click", function() {
        if ( $(this).is(":checked") ) {
            searchfield.show();
            //if a search has been performed, update results
            if ($(".search-results").is(":visible")) {
                $(".search-form").submit();
            }
        } else {
            if ( searchfield.val().length == 0) {
                $companyNameOption.slideUp();
            }
        }
    });

    /* CAPTURING CLICK OUTSIDE THE SEARCH FORM ------*/
    $(document).on("click", function(e) {
        console.log($(e.target).closest("form.search-form").length);
        //If click is outside from area
        if ($(e.target).closest("form.search-form").length > 0) {
            $companyNameOption.show();
        } else {

            //If checkbox is checked or if search field is not empty
            if ( $("#search-comp").is(":checked") || searchfield.val().length > 0 )  {
                console.log("hello");
                $companyNameOption.show();
            } else {
                $companyNameOption.slideUp();
            }
        }
    });


    /* SUBMIT SEARCH --------- */
    $(".search-form").on("submit", function() {
        $(".search-btn").trigger("click", false);
        return false;
    });

    /* SEARCH BUTTON - filterClicked is passed as True when a filter is clicked --------- */
    $(".search-btn").on("click", function(event, filterClicked) {

        //if a filter was clicked on the mobile version, show no delay
        if (filterClicked && isMobile) {
            $(".progress-indicator").hide();
            $(".awards-block").hide();
            $(".search-results").show();
        } else {
            $(".search-results").hide();
            $(".progress-indicator").show();
            setTimeout(function() {
                $(".progress-indicator").hide();
                $(".awards-block").hide();
                $(".search-results").show(); /* slideDown('slow'); causes vertical shift at the end of the animation --- */
            }, 1000);
        }
    });

    /* CLEAR SEARCH FIELD--------- */
    $(".clear-search").on("click", function() {
        var $this = $(this);
        $companyNameOption.show();
        $this.closest(".search-form").find(".search-field").val("");
        $this.hide();

        if ($("#clear-all").is(":hidden")) {
            $(".search-results").slideUp('slow');
            $(".awards-block").show();
            $(".search-field").focus();
        } else {

        }
    });


    /* GLOBAL AWARD WINNERS
     -------------------------------------------------------------- */
    $(".awards-block").on("click", "li", function() {
        var awardLink = $(this).find("a:eq(0)").attr("href");
        location.href = awardLink;
    });


    /* FILTERING
     -------------------------------------------------------------- */

    /* INITIALIZE FILTERS --------- */

    // Identifying the last node
    $(".filters").find("a").each(function() {
        var $this = $(this);
        if ($this.closest("li").find("ul").length <= 0) {
            $this.closest("li").addClass("last-node");	//Add a class to all the last-nodes
        }
    });

    // Configuration: Max Number of Options Shown By Default
    var maxItemsPerList = 5,
        maxItemsToShow = 5;

    /* FILTER PARTNERS LIST: FACET SELECTION --------- */

    $("#browsePartners").on("click", "a:not(.showmore, .close-filter, .submit-filter)", function() {
        //Set filterClicked to true (Search form submit behavior is different in mobile devices)
        filterClicked = true;

        var $this = $(this),
            topMostParent = $this.closest(".accordion-level"),
            facetIsOpen = $this.parent("li").hasClass("accordion-level open"),
            selFilters = topMostParent.find(".selected-filters"),
            currFilter = topMostParent.children("a").find("span"),
            clickedItem = $this.html(),
            isLeafNode = $this.next("ul").length === 0 && !( $this.closest("li").hasClass("accordion-level") );

        // If clear all link is clicked
        if ( $this.attr("id") != null && $this.attr("id").indexOf("clear-all") > -1 ) {
            clearFilters();

            //If a 	closed facet is clicked
        } else if ( !facetIsOpen ) {
            //If the link is a leaf node (has no children)
            if ( isLeafNode ) {

                var $currLi = $this.closest("li");

                // Select current item and hide the items at the same level
                $currLi.siblings().hide();
                $currLi.addClass("selected");

                // Append Selected Item to the Selected Filters List (Pawan: Potential function()?)
                var selItem = "<div><a href='javascript:;' class='remove-filter'><span>" + clickedItem + "</span></a></div>";
                selFilters.append(selItem);
                selFilters.addClass("showing");
                //	selFilters.addClass("final"); // Means no more filters for this facet??

                $(".clearall").show();

                // Show Applied Checkmark
                currFilter.addClass("applied"); //Add checkmark when a filter is applied

                // Remove Show More link after a leaf node is selected (All filters are hidden at this point)
                $currLi.closest(".accordion-level").find(".showmore").remove();

                // Show search results
                $(".search-btn").trigger("click", true); //Parameter passed indicates that a filter was clicked

                //If the link has children or is a selected filter
            } else {
                // if selected filter is clicked -- User wants to remove filters below it.
                if ( $this.closest(".selected-filters").length > 0 ) {

                    //Find the filter link with the matcing text (to the one that was clicked)
                    var toRemove = $this.parent().text(),
                        thisFilter = $this.closest(".accordion-level").find("ul.sub-filters").find("a:contains(" + toRemove + ")").filter(function()
                        {
                            return $(this).text() === toRemove;
                        });

                    //If the filter to be removed is a leaf node, show it's siblings.
                    if (thisFilter.closest("li").hasClass("last-node")) {
                        thisFilter.closest("li").siblings("li").show();
                        showMore( thisFilter.closest("ul") ); //Check if it has more items then the max we need to show

                        //If the filter to be removed is not a leaf node, click on it
                    } else {
                        thisFilter.trigger("click", true);
                    }
                    $this.parent("div").nextAll("div").remove(); // Remove all subsequent "selected" filters
                    $this.parent("div").remove(); // Remove current filter

                    //If the filter to be removed is not visible,
                    //remove the selected class and show it's siblings and their children
                    if ( ! thisFilter.is(":visible") ) {
                        thisFilter.closest("li").removeClass("selected").show();
                        thisFilter.closest("li").siblings().show();
                        showMore( thisFilter.closest("ul") );
                    }
                    //Show Progress Indicator and update search results
                    $(".search-btn").trigger("click", true);

                    //Check if there are any remaining filters in the selected filter area
                    checkSelectedFilterStats();

                    // if a filter option is clicked
                } else {
                    var $currLi = $this.closest("li"), //Parent LI
                        nextList = $this.siblings("ul"), //Sub filter
                        numParents = $this.parents("ul").length, //Number of parents
                        selItem = "<div><span>"; //Initial HTML to add a selected filter

                    //If selected filter has any sub filters and if they are hidden
                    if ( nextList.length > 0 && nextList.is(":hidden") ) {
                        nextList.show();	//Show sub filters
                        nextList.children("li").show(); //Show sub filter options
                        $currLi.addClass("open");
                        selFilters.show();	//Show the selected filters area

                        //If a first level filter was clicked (E.g., Location),
                        if (numParents === 1) {
                            //Check if something was selected in the past
                            if (nextList.children(".selected").length > 0) {
                                //Hide all filters other than the previously selected one
                                nextList.children("li").not(".selected").hide();
                            }
                            //Check if search results are hidden
                            /*	if ( ! $(".search-results").is(":visible")) {
                             //Submit the search form to show results.
                             $(".search-btn").trigger("click", true);
                             } */
                        }

                        //If 2nd or subsequent level filter is clicked
                        if (numParents > 1) {
                            $currLi.addClass("selected");

                            // Create a selected filter eleent
                            selItem = "<div><a href='javascript:;' class='remove-filter'><span>" + $currLi.children("a").html() + "</span></a></div>";

                            // Add it to the selected Filters area
                            selFilters.append(selItem);

                            //Show the selected filters area
                            selFilters.addClass("showing");

                            //Hide all siblings of the current selected filter
                            $currLi.siblings().hide();

                            //Add checkmark to show that a filter is applied
                            currFilter.addClass("applied");

                            //Show the clear all link
                            $(".clearall").show();

                            //Submit filter form to show search results
                            $(".search-btn").trigger("click", true);
                        }
                        //Add show more link to list of sub filters
                        showMore(nextList);

                        // When a previously selected filter (non last node) filter is clicked
                        // This condition occurs only when a filter in the middle of the selected filter list is removed
                    } else {

                        $currLi.removeClass("selected open");

                        //Show the filter siblings
                        $currLi.siblings().show();

                        //Add "Show MOre" link if necessary
                        showMore( $currLi.closest("ul"));

                        //Add a selected filter
                        selItem = selItem + $currLi.children("a").text() + "</div>";
                        selFilters.append(selItem);
                        selFilters.addClass("showing");

                        // Add checkmark when a filter is applied
                        currFilter.addClass("applied");
                        $(".clearall").show();

                        //Identify all children and grandchildren lists
                        var $childrenUls = $currLi.find("ul") ;

                        //Remove "Show More" link from all children and grand children nodes
                        $.each( $childrenUls, function() {
                            $(this).hide();
                            $(this).siblings(".showmore").remove();
                            $(this).children("li").removeClass("selected");
                        });
                    }
                }
            }

            // If a facet section is already open
        } else {
            //Prserve all selections and close the filters
            topMostParent.removeClass("open");
            selFilters.hide();
            $this.siblings("ul").hide(); // Hide the filters under the facet
            $this.siblings("a.showmore").remove();
        }
    });


    /* SHOW "Show More" LINK: IF # of Items > Max Items Per List --------- */
    function showMore( list ) {
        list.parents("ul:eq(0)").next(".showmore").remove();
        list.next(".showmore").remove();
        if ( list.children("li").length > maxItemsPerList ) {
            list.children( "li:gt( " + maxItemsToShow + ")" ).hide();
            list.after('<a href="javascript:;" class="showmore">Show More</a>');
        }
    }

    /* USERS CLICKING "SHOW MORE / SHOW LESS" LINKS --------- */
    $("#browsePartners").on("click", ".showmore", function() {
        var $this = $(this),
            $siblingList = $this.siblings("ul").children("li:gt(" + maxItemsToShow + ")"),
            showLess = $this.hasClass("showless");
        if ( showLess ) {
            $siblingList.hide();
            $this.text("Show More").removeClass("showless");
        } else {
            $siblingList.show();
            $this.text("Show Less").addClass("showless");
        }
    });



    /* HOVERTIP:  Specialty Areas Tooltip --------- */
    $(".hint").hover(function() {
        $(this).find(".tooltip").show();
    }, function() {
        $(this).find(".tooltip").hide();
    });


    /* AWARD WINNING PARTNERS - CHECKBOX --------- */
    $("#award-winning").on("click", function() {
        $(this).closest("label").toggleClass("checked");

        if ( $(this).is(":checked") ) {
            $(".clearall").show();
        }

        // Submit the search form to show delay and search results
        $(".search-btn").trigger("click", true);
    });


    /* SHOW / HIDE MAP  --------- */
    $(".show-map").on("click", "a", function() {
        $("#map").toggle();
        $(this).find("strong").toggleText("Hide Map", "Show Map");
    });


    /* WINDOW RESIZE --------- */
    checkVersionSize(); //Function checks if mobile version is showing
    $(window).on("resize", function() {
        checkVersionSize(); //Function checks if mobile version is showing

        //truncateText(); //Check text awards block text length
    });


    /* RESPONSIVE CODE
     -------------------------------------------------------------------- */

    /* RESPONSIVE / MOBILE BUTTONS: Filter and Recently Viewed --------- */
    $(".mobile-tabs").on("click", "a", function() {
        var $this = $(this),
            dropSheet = $(".pf-dropsheet"),
            refineFilters = $(".refine-block"),
            filters = refineFilters.find("#browsePartners"),
            recentSearch = refineFilters.find(".recent-searches"),
            fHeader = refineFilters.find("h3");

        dropSheet.show();
        refineFilters.show();
        filters.hide();
        recentSearch.hide();
        $("body").addClass("overlay-open"); //removes scrollbars from page

        if ($this.attr("id").indexOf("show-filters") > -1) {
            // Show Filters
            filters.show();
        } else {
            // Show Recently Viewed.
            recentSearch.show().addClass("open");
        }

        //50px accomodates the height of the Clear All button
        var scrollHeight = refineFilters.height() - fHeader.eq(0).outerHeight(true) - refineFilters.find(".button-bar:visible").outerHeight(true) - 50;
        refineFilters.find(".filter-scroll:visible").css("height", scrollHeight + "px");
    });


    /* DISMISS MOBILE OVERLAY --------- */
    $(".refine-block").on("click", ".close-filter", function(){
        var	dropSheet = $(".pf-dropsheet"),
            refineFilters = $(".refine-block");
        dropSheet.hide();
        $("body").removeClass("overlay-open"); //restores scrollbars to original
        refineFilters.hide();
    });

    /* SUBMIT FILTERS IN MOBILE OVERLAY --------- */
    $(".refine-block").on("click", ".submit-filter", function(){

        var	dropSheet = $(".pf-dropsheet"),
            refineFilters = $(".refine-block");
        //If any filter is applied.
        if ($(".clearall").is(":visible")) {
            $("#show-filters").addClass("applied"); // Add check next to the label "Filter Partners"
            dropSheet.hide();
            refineFilters.hide();
            $("body").removeClass("overlay-open"); //restores scrollbars to original
            $(".search-btn").trigger("click", false);
        } else {
            $("#show-filters").removeClass("applied"); // Add check next to the label "Filter Partners"
            dropSheet.hide();
            refineFilters.hide();
            $("body").removeClass("overlay-open");
        }
    });
});


// Check to see if selected filter section is empty
function checkSelectedFilterStats() {
    $(".selected-filters").each(function() {
        var $this = $(this);
        if ($this.find("div").length <= 0) {
            $this.removeClass("showing");
            $this.closest(".accordion-level").children("a").find("span").removeClass("applied");
        }
    });
    if ( $(".applied").length === 0 ) {
        $(".clearall").hide();
    }
}


/* CLEAR ALL FILTERS --------- */
function clearFilters() {
    $(".search-results").slideUp('slow');

    var refineBlock = $(".refine-filters");

    refineBlock.find("li").removeClass("open selected").removeAttr("style");
    refineBlock.find(".selected-filters").hide().children("div").remove();
    refineBlock.find("ul").removeAttr("style");
    refineBlock.find("a").children("span").removeClass("applied");
    refineBlock.find("#award-winning").removeAttr("checked");

    $(".awards-block").show();
    $(".clearall").hide();
    $("#show-filters").removeClass("applied"); // Remove check next to the label "Filter Partners"
}

/* FUNCTION CHECKS THE WINDOW SIZE, ADJUSTS RESPONSIVE BEHAVIOR -------*/
function checkVersionSize() {

    document.body.style.overflow = "hidden";
    var winWidth = $(window).width();
    document.body.style.overflow = "";
    // console.log(winWidth + "px");

    if (winWidth > 768) {
        $(".refine-block").show();

        //Remove overflow hidden styles from body
        $("body").removeClass("overlay-open");

        //Show recent searches block and filter
        $("#browsePartners").show();
        $(".recent-searches").show().addClass("open");

        //Remove scrollbars from filter lists
        $(".filter-scroll").removeAttr("style");

        isMobile = false; //set mobile parameter to false

    } else {
        $(".pf-dropsheet").hide();
        $(".refine-block").hide();

        isMobile = true; //set mobile parameter to true
    }
}


/* PLUG-IN: Toggle Text - Used for togglign text: Show Map / Hide Map --------- */
$.fn.toggleText = function (value1, value2) {
    return this.each(function () {
        var $this = $(this),
            text = $this.text();

        if (text.indexOf(value1) > -1)
            $this.text(text.replace(value1, value2));
        else
            $this.text(text.replace(value2, value1));
    });
};


var awardText = [];


/*function truncateText() {
 //Copy original text into an array
 if (awardText.length === 0) {
 $(".awards-block li").each(function() {
 var logoEle = $(this).find("p").children("a");
 awardText.push(logoEle.text());
 });
 }

 var maxLength = 80,
 wWidth = $( window ).width();
 console.log(wWidth);
 // If window width is less than 800px & more than 750, truncate the text
 if (wWidth < 800 && wWidth > 750) {
 $(".awards-block li").each(function() {

 var logoEle = $(this).find("p").children("a");
 logoText = logoEle.text(),
 textLength = logoText.length;
 if (textLength > maxLength) {
 logoText = logoText.substr(0, maxLength-3) + "...";
 logoEle.text(logoText);
 }
 });
 } else {
 $(".awards-block li").each(function(i) {
 var logoEle = $(this).find("p").children("a");

 logoEle.text(awardText[i]);
 });
 }
 } */