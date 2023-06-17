export function showMessage(t,e,o,n){$("#message-dialog-title").removeClass(),$("#message-dialog-content").removeClass(),$("#message-dialog-button").removeClass(),$("#message-dialog-title").addClass(e),$("#message-dialog-content").addClass(o),$("#message-dialog-button").addClass(n),$("#message-dialog-content").text(t),$("#message-dialog").modal("show")}export function showSuccessfulMessage(t){showMessage(t,"text-success","text-success","btn btn-success")}export function showErrorMessage(t){showMessage(t,"text-danger","text-danger","btn btn-danger")}export function showSuccessfulChildModal(t){$("#message-dialog").css("z-index",parseInt($("#update_modal").css("z-index"))+1),showSuccessfulMessage(t)}export function showErrorChildModal(t){$("#message-dialog").css("z-index",parseInt($("#update_modal").css("z-index"))+1),showErrorMessage(t)}export function sendRequest(t,e,o,n,a,r){$.ajax({url:t,method:e,data:o,contentType:n,success:a,error:r})}export function getBase64String(t){return new Promise((e,o)=>{let n=new FileReader;n.readAsDataURL(t),n.onload=()=>{e(n.result)},n.onerror=()=>{o(n.error)}})}export function changePage(t){$("html").html(t)}export function getFormData(t){let e=new FormData($(t)[0]);var o={};for(let n of e.entries())o[n[0]]=n[1];return o}export function getURL(t){return"/QL_TaiSan/"+t}export function getMethod(){return $("#save-form").attr("method")}export function getJsonData(t){return JSON.stringify(t)}export function getContentType(t){return"json"===t?"application/json":"text"}export function callInputImageEvent(){$("body").on("change","#inputImageFile",async function(){if(!this.files[0].type.startsWith("image/")){$("#inputImage").attr("src","");return}$("#inputImage").attr("src",await getBase64String(this.files[0]))}),$("body").on("click","#inputImage",function(){$("#inputImageFile").click()})}export function callDatePickerEvent(){$("body").on("focus","input.datepicker",function(){$(".datepicker").datepicker({dateFormat:"dd/mm/yy"})}),$("body").on("focus","input.datetimepicker",function(){$(this).datetimepicker({dateFormat:"dd/mm/yy",timeFormat:"HH:mm",controlType:"select",oneLine:!0,stepMinute:5})})}export function disabledButton(t){$(t).attr("disabled",!0)}export function getCurrentResource(){return window.location.href.split("/")[4]}export function callAddButtonEvent(){$("body").on("click",'button[name="add"]',function(){let t=getCurrentResource()+"/add";$(this),sendRequest(getURL(t),"GET",{},"text",function(t){0!==$("body").find("#update_modal").length&&$("body").find("#update_modal").remove(),$("body").append(t),$("#update_modal").modal("show")},function(t){showErrorMessage(t.responseText)})})}export function reloadNumRecords(t,e){let o=t.not(".emptyRow").length;$(e).text(o)}export function reloadButtonClick(t,e){$("body").on("click",".reloadBtn",function(){reloadData(t,e)})}export function destroyReloadButtonClick(){$("body").off("click",".reloadBtn")}export function reloadData(t,e){let o=getCurrentResource()+"/view";"function"==typeof t&&(o+=t()),sendRequest(getURL(o),"GET",{},{},function(t){$(".content-wrapper table tbody tr").detach();var o=$(t).find(".content-wrapper .table tbody tr");o.appendTo($(".content-wrapper .table tbody")),reloadNumRecords(o,"#numRecords"),reloadFilter(),reloadInputSearch(),reloadDatePicker(),"function"==typeof e&&e()},function(t){showErrorMessage(t.responseText)})}export function saveAndNew(t){let e=getCurrentResource()+"/edit";sendRequest(getURL(e),getMethod(),getJsonData(t),getContentType("json"),function(){$("#update_modal").modal("hide"),$("#update_modal").on("hidden.bs.modal",function(){$('button[name="add"]').click()}),reloadData()},function(t){$("#message-dialog").css("z-index",parseInt($("#update_modal").css("z-index"))+1),showErrorChildModal(t.responseText)})}export function saveAndClose(t){let e=getCurrentResource()+"/edit";getCurrentResource(),sendRequest(getURL(e),getMethod(),getJsonData(t),getContentType("json"),function(){$("#update_modal").modal("hide"),reloadData()},function(t){$("#message-dialog").css("z-index",parseInt($("#update_asset").css("z-index"))+1),showErrorChildModal(t.responseText)})}export function callRemoveModal(){$("body").on("hidden.bs.modal","#update_modal",function(){$(this).remove()})}export function callTableRowClickEvent(){$("body").on("click",".content-wrapper .table tbody tr:not(.emptyRow)",function(){var t;let e=getCurrentResource()+"/view";sendRequest(getURL(e+"?id="+$(this).find("td:first").text()),"GET",{},"text",function(t){$("body").append(t),$("#update_modal").modal("show")},function(t){showErrorMessage(t.responseText)})})}export function showModalEvent(t){$("body").on("shown.bs.modal",".modal",function(){"function"==typeof t&&t()})}export function destroyShowModalEvent(){$("body").off("shown.bs.modal",".modal")}export function destroyTableRowClickEvent(){$("body").off("click",".content-wrapper .table tbody tr:not(.emptyRow)")}export function reloadFilter(){$(".filter").prev().text("Tất cả")}export function reloadInputSearch(){$("body").find("#search-input").val("")}export function reloadDatePicker(){$("body").find("#startDate").val(""),$("body").find("#endDate").val("")}export function dateFilter(){var t="DD/MM/YYYY HH:mm",e=moment($("body").find("#startDate").val(),t),o=moment($("body").find("#endDate").val(),t);e.isValid()||(e=moment("1/1/0001 00:00",t)),o.isValid()||(o=moment("31/12/9999 23:59",t));var n=$("body").find(".table-wrapper table tbody tr:not(.emptyRow)");n.each(function(){moment($(this).find("td.dateIndex").text(),t).isBetween(e,o,null,"[]")?$(this).show():$(this).hide()}),reloadNumRecords(n.filter(function(){return"none"!==$(this).css("display")}),"#numRecords")}export function nameFilter(){let t=$("body").find(".filter").prev().text(),e="Tất cả"===t?"":t;$("body").find(".filter").prev().text(t),$("body").find(".content-wrapper .table tbody tr:not(.emptyRow)").each(function(){let t=$(this).find("td.index").text();t.includes(e)?$(this).show():$(this).hide()});let o=$("body").find(".content-wrapper .table tbody tr:not(.emptyRow)").filter(function(){return"none"!==$(this).css("display")});reloadNumRecords(o,"#numRecords")}export function dateAndNameFilter(){let t=$("body").find(".filter").prev().text();var e="DD/MM/YYYY",o=moment($("body").find("#startDate").val(),e),n=moment($("body").find("#endDate").val(),e);o.isValid()||(o=moment("1/1/0001",e)),n.isValid()||(n=moment("31/12/9999",e));var a=$("body").find(".table-wrapper table tbody tr:not(.emptyRow)");let r="Tất cả"===t?"":t;a.each(function(){var t=$(this).find("td.index").text(),a=moment($(this).find("td.dateIndex").text(),e);t.includes(r)&&a.isBetween(o,n,"day","[]")?$(this).show():$(this).hide()}),reloadNumRecords(a.filter(function(){return"none"!==$(this).css("display")}),"#numRecords")}export function callFilterEvent(){$("body").on("click",".filter .dropdown-item",function(){$(this).parent().prev().text($(this).text()),0===$("body").find("#startDate").length?nameFilter():dateAndNameFilter()}),$("body").on("change","#startDate, #endDate",function(){0===$("body").find(".filter").length?dateFilter():dateAndNameFilter()})}export function callInputSearchEvent(t){$("body").on("keypress","#search-input",function(e){if(13===e.keyCode){let o=getCurrentResource()+"/view";var n=$(this).val(),a=o+"?"+$.param({action:"search",key:n});"function"==typeof t&&(a=o+"?action=searchAsset&key="+n+t()),sendRequest(getURL(a),"GET",{},"text",function(t){$(".table tbody tr").detach();var e=$(t).find(".table tbody tr");e.appendTo($(".table tbody")),reloadNumRecords(e,"#numRecords"),reloadFilter(),reloadDatePicker()},function(t){showErrorMessage(t.responseText)})}})}export function destroyInputSearchEvent(){$("body").off("keypress","#search-input")}function checkEmptyTable(t){let e=$(t).find("tr:not(.emptyRow)").length;0===e?$(t).find(".emptyRow").removeClass("d-none"):$(t).find(".emptyRow").addClass("d-none")}export function emptyDropdownMenuEvent(){$("body").on("change",".table tbody",function(){let t=$(".table tbody tr:not(.emptyRow)").length;0===t?$(".emptyRow").show():$(".emptyRow").hide()})}export function destroySearchDropdownMenuEvent(){$("body").off("input",".modal input.action")}export function callSearchDropdownMenuEvent(t){var e;$("body").on("input",".modal input.action",function(){let o=$(this).attr("action");if("function"==typeof t&&!1===t(o))return!1;let n=getCurrentResource()+"/view";var a=$(this).parent().find(".dropdown-menu"),r=a.find(".dropdown-item:not(.emptyRow)");$(this).attr("data-value","");let d=$(this).val();d.length>0?(clearTimeout(e),e=setTimeout(function(){var e=n+"?action="+o+"&key="+d;if("function"==typeof t&&"searchAsset"===o){let i=$("input.action").filter(function(){return"searchDepartment"===$(this).attr("action")}).attr("data-value");e+="&departmentId="+i}sendRequest(getURL(e),"GET",{},"text",function(t){r.detach();var e=$(t).find("input.action").filter(function(){return $(this).attr("action")===o}).next().find(".dropdown-item:not(.emptyRow)");0===$(e).length?$(a).find(".emptyRow").show():($(a).find(".emptyRow").hide(),$(e).appendTo($(a))),$(a).addClass("show")},function(t){showErrorMessage(t.reponseText)})},500)):($(r).detach(),$(a).removeClass("show"))})}export function chooseItemFromDropdownMenu(){$("body").on("click",".modal .dropdown-item:not(.table-item)",function(){var t=$(this).find("span").first().attr("data-value"),e=$(this).find("span").eq(1).attr("data-value"),o=$(this).parent(),n=$(o).prev();$(n).attr("data-value",t),$(n).val(e),"true"===o.attr("display-id")&&$(n).val(t),o.find(".dropdown-item").detach(),o.removeClass("show")})}export function destroyChooseItemFromDropdownMenu(){$("body").off("click",".modal .dropdown-item:not(.table-item)")}export function destroyActionButtonClickEvent(){$("body").off("click","button.action")}export function callTableInputEvent(t){$("body").on("input",".modal table input",function(){t(this)})}export function destroyTableInputEvent(){$("body").off("input",".modal table input")}export function callDeleteTableItemClickEvent(t){$("body").on("click",".modal table button",function(e){let o=$(e.target.closest("tr")).parent();$(e.target.closest("tr")).remove(),t(e),checkEmptyTable(o),reloadNumRecords($(".modal table tbody tr"),"#modalNumRecords")})}export function destroyDeleteTableItemClickEvent(){$("body").off("click",".modal table button")}export function validNumberTypeInput(){$("body").on("input","input.checkNumberType",function(t){let e=$(this),o=e.val(),n=o.replace(/[^0-9]/g,"");if(n!==o)return event.target.value=n,!1;let a=parseInt(e.attr("max")),r=parseInt(e.attr("min"));n=n>a?a:n<r?r:n,e.val(n)})}export function callTableItemClickEvent(t,e){$("body").on("click",".modal .dropdown-item.table-item",function(){var o=$(this).find("span").first().attr("data-value");if("function"==typeof t&&!1===t(this))return!1;var n=$(this).parent();n.find(".dropdown-item").detach(),n.removeClass("show"),n.prev().val("");let a=getCurrentResource()+"/view?action=addItem&key="+o;if("assetDispose"===getCurrentResource()){let r=$("input.action").filter(function(){return"searchDepartment"===$(this).attr("action")}).attr("data-value");a+="&departmentId="+r}sendRequest(getURL(a),"GET",{},"",function(t){var n=!1;if($(".modal table tbody").find("tr").each(function(){if($(this).find("button").attr("data-value")===o)return n=!0,!1}),n)return!1;$(t).find("table tbody tr:not(.emptyRow)").appendTo($(".modal table")),"function"==typeof e&&e(),checkEmptyTable($(".modal table tbody")),reloadNumRecords($(".modal table tbody tr:not(.emptyRow)"),"#modalNumRecords")},function(t){showErrorMessage(t.responseText)})})}export function destroyTableItemClickEvent(){$("body").off("click",".modal .dropdown-item.table-item")}