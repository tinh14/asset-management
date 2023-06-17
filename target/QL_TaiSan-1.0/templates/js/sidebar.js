import {getURL, getCurrentResource, sendRequest, showErrorMessage} from './common.js';

export function showSubButtonClickEvent() {
    $('body').on('click', '.sub-btn', function () {
        $(this).next('.sub-menu').slideToggle();
        $(this).find('.dropdown').toggleClass('rotate');
    });
}

function hide(sidebar, contentWrapper) {
    $(sidebar).animate({opacity: 0}, 300);
    $(sidebar).css({'pointer-events': 'none'});
    $(contentWrapper).animate({marginLeft: 0}, 500);
}

function show(sidebar, contentWrapper) {
    const sidebarWidth = $(sidebar).css('width');
    $(sidebar).animate({opacity: 1}, 500);
    $(sidebar).css({'pointer-events': 'auto'});
    $(contentWrapper).animate({marginLeft: sidebarWidth}, 300);
}

export function showHideSidebarEvent() {
    const sidebar = $('body .side-bar');
    const contentWrapper = $('body .content-wrapper');

    $('body').on('click', '#hide-sidebar-button', function () {
        const isDisplayed = (sidebar.css('opacity') === "0") ? false : true;
        // Ẩn
        if (isDisplayed === true) {
            hide(sidebar, contentWrapper);
        } else {
            show(sidebar, contentWrapper);
        }
    });

    $('body').on('click', '#closeSidebarBtn', function () {
        hide(sidebar, contentWrapper);
    });
}

export function renderResourceClickEvent() {

    $('body').on('click', '.side-bar a:not(.sub-btn)', function () {
        if ($(this).attr("data-value") === getCurrentResource()) {
            return false;
        }

        var url = $(this).attr("data-value") + "/view";
        const $this = $(this);
        var items = $('.menu a:not(.sub-btn)');
        items.filter('.sub-item').removeClass('selected-item');
        items.filter(':not(.sub-item)').removeClass('selected-item');

        if ($(this).attr('data-value') === "logout") {
            sendRequest(getURL('logout'), "POST", {}, {},
                    function () {
                        window.location.href = getURL("login");
                    },
                    function (xhr) {
                        showErrorMessage(xhr.responseText);
                    });
            return false;
        }

        sendRequest(getURL(url), "GET", {}, {},
                function (response) {
                    const contentWrapper = $('body .content-wrapper');
                    const sidebar = $('body .side-bar');

                    $(contentWrapper).find('form-wrapper').detach();
                    $(contentWrapper).html($(response).find('.form-wrapper'));

                    $this.addClass("selected-item");

                    $('title').text($(response).filter('title').text());

                    const screen_width = window.innerWidth;
                    console.log(screen_width);
                    if (screen_width < '600') {
                        hide(sidebar, contentWrapper);
                    }

                    history.replaceState(null, null, getURL(url));
                    $(document).trigger('routeChange');
                },
                function (xhr) {
                    showErrorMessage(xhr.responseText);
                }
        );
    });
}

export function renderSelectedItem() {
    $(document).ready(function () {
        var currentUrl = window.location.href;
        var lastPart = currentUrl.split("/")[4];

        var subItem = $('a[data-value="' + lastPart + '"]');
        subItem.addClass("selected-item");

        var parent = subItem.parent();

        if ($(parent).hasClass("item")) {
            return;
        }
        parent.slideToggle();
        $(parent.parent()).find(".dropdown").toggleClass('rotate');
    });
}

