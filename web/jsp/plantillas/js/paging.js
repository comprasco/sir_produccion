function Pager(tableName, itemsPerPage) {
    this.tableName = tableName;
    this.itemsPerPage = itemsPerPage;
    this.currentPage = 1;
    this.pages = 0;
    this.inited = false;

    this.showRecords = function (from, to) {
        console.log("PAGING FROM "+from+" TO "+to);
        var rows = document.getElementById(tableName).rows;
        // i starts from 1 to skip table header row
        for (var i = 1; i < rows.length; i++) {
            console.log("NUMERO FILAS "+rows.length);
            if (i < from || i > to){
                console.log("NONE");
                rows[i].style.display = 'none';}
            else{
                console.log("SISA");
                rows[i].style.display = '';}
        }
    }

    this.showPage = function (pageNumber) {
        if (!this.inited) {
            alert("not inited");
            return;
        }

        var oldPageAnchor = document.getElementById('pg' + this.currentPage);
        oldPageAnchor.className = 'pg-normal';

        this.currentPage = pageNumber;
        var newPageAnchor = document.getElementById('pg' + this.currentPage);
        newPageAnchor.className = 'pg-selected';

        if (pageNumber === 1) {
            var from = 1;
            var to = from + itemsPerPage - 1;
            this.showRecords(from, to);
        } else {
            var from = (pageNumber - 1) * itemsPerPage + 1;
            var to = from + itemsPerPage - 1;
            this.showRecords(from, to);
        }

    }

    this.prev = function () {
        if (this.currentPage > 1)
            this.showPage(this.currentPage - 1);
    }

    this.next = function () {
        if (this.currentPage < this.pages) {
            this.showPage(this.currentPage + 1);
        }
    }

    this.init = function () {
        var rows = document.getElementById(tableName).rows;
        var records = (rows.length - 1);
        this.pages = Math.ceil(records / itemsPerPage);
        this.inited = true;
    }

    this.showPageNav = function (pagerName, positionId) {
        if (!this.inited) {
            alert("not inited");
            return;
        }
        var element = document.getElementById(positionId);

        var pagerHtml = '<input id="btn_paginator" type="button" onclick="' + pagerName + '.prev();" value="&#171; Ant"> | ';
        for (var page = 1; page <= this.pages; page++)
            pagerHtml += '<span id="pg' + page + '" class="pg-normal" onclick="' + pagerName + '.showPage(' + page + ');">' + page + '</span> | ';
        pagerHtml += '<input id="btn_paginator" type="button" onclick="' + pagerName + '.next();" value="Sig &#187;">';

        element.innerHTML = pagerHtml;
    }
}

