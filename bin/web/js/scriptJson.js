altView = false;

function changeView() {
	altView = !altView;
	if (altView){
		document.getElementsByClassName("simpleView")[0].style.display = "none";
		document.getElementsByClassName("altView")[0].style.display = "flex";
	} else {
		document.getElementsByClassName("simpleView")[0].style.display = "block";
		document.getElementsByClassName("altView")[0].style.display = "none";
	}
}

function getContent(location) {
    var xhttp = new XMLHttpRequest();
    xhttp.overrideMimeType("application/json");
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
						storeData(this);
            presentData(this);
        }
    };
    xhttp.open("GET", "json/" + location + ".json", true);
    xhttp.send();
}

function storeData(json) {
	allDrawers = JSON.parse(json.responseText);

	checkedOutDrawers = [];
	errorDrawers = [];
	overdueDrawers = [];
	remainingDrawers = [];

	for (i = 0; i < allDrawers.length; i++){
		var drawer = allDrawers[i];
		if (drawer.isTakenOut != "true" && drawer.isInError != "true" && drawer.isOpen != "true"){
			remainingDrawers.push(drawer);
			continue;
		}
		if (drawer.isTakenOut === "true"){
			checkedOutDrawers.push(drawer);
		}
		if (drawer.isInError === "true" || drawer.isOpen === "true"){
			errorDrawers.push(drawer);
		}
		if (drawer.isTakenOut === "true" && drawer.overdue){
			overdueDrawers.push(drawer);
		}
	}
}

function presentData(json) {

    var tableContainer = document.getElementById("dataContainer");
    insertFullHeader(tableContainer);

    var tableBody = tableContainer.createTBody();

    for (i = 0; i < allDrawers.length; i++) {
        var row = tableBody.insertRow(i);
        var drawerNumberInnerHTML = "<a href=\"http://kiosk.aut.ac.nz/LockerBox/Drawers/Edit/" +
            allDrawers[i].drawerId + "\" >" + allDrawers[i].drawerNumber + "</a>";
        row.insertCell(0).innerHTML = drawerNumberInnerHTML;
				row.insertCell(1).innerHTML = allDrawers[i].isTakenOut;
        row.insertCell(2).innerHTML = allDrawers[i].isOpen;
        row.insertCell(3).innerHTML = allDrawers[i].isInError;
        row.insertCell(4).innerHTML = allDrawers[i].rackId;
        row.insertCell(5).innerHTML = allDrawers[i].barcode;
        row.insertCell(6).innerHTML = allDrawers[i].rfid;
        row.insertCell(7).innerHTML = allDrawers[i].lastStudent;
        row.insertCell(8).innerHTML = allDrawers[i].note;

        var logEntryHTML = "<a href=\"http://kiosk.aut.ac.nz/LockerBox/Drawers/Logs/" +
            allDrawers[i].drawerId + "\" >" + allDrawers[i].drawerLog.logEntry + "</a>";
        row.insertCell(9).innerHTML = logEntryHTML;

        row.insertCell(10).innerHTML = allDrawers[i].drawerLog.logTimestamp;
    }
	presentAltTable(document.getElementById("inErrorTable"), errorDrawers);
	presentAltTable(document.getElementById("checkedOutTable"), checkedOutDrawers);
	presentAltTable(document.getElementById("remainingTable"), remainingDrawers);
	presentAltTable(document.getElementById("overdueTable"), overdueDrawers);
}

function presentAltTable(table, drawers){
	insertAltHeader(table)
	var tableBody = table.createTBody();
	for (i = 0; i < drawers.length; i++) {
		var row = tableBody.insertRow(i);
		var drawerNumberHTML = "<a href=\"http://kiosk.aut.ac.nz/LockerBox/Drawers/Edit/" +
				drawers[i].drawerId + "\" >" + drawers[i].drawerNumber + "</a>";
		row.insertCell(0).innerHTML = drawerNumberHTML;
		row.insertCell(1).innerHTML = drawers[i].isTakenOut;
		row.insertCell(2).innerHTML = drawers[i].isOpen;
		row.insertCell(3).innerHTML = drawers[i].isInError;
		row.insertCell(4).innerHTML = drawers[i].lastStudent;
		row.insertCell(5).innerHTML = drawers[i].note;
		var logEntryHTML = "<a href=\"http://kiosk.aut.ac.nz/LockerBox/Drawers/Logs/" +
				drawers[i].drawerId + "\" >" + drawers[i].drawerLog.logEntry + "</a>";
		row.insertCell(6).innerHTML = logEntryHTML;
		row.insertCell(7).innerHTML = drawers[i].drawerLog.logTimestamp;
	}
}

function insertFullHeader(tableContainer) {
    var header = tableContainer.createTHead();
    var row = header.insertRow(0);
    // row.insertCell(0).outerHTML = "<th class=\"drawer-number-cell\">Drawer Number</th>";
		insertHeaderCell(row.insertCell(0), "standard-cell", "Drawer Number");
		insertHeaderCell(row.insertCell(1), "standard-cell", "Checked Out");
		insertHeaderCell(row.insertCell(2), "standard-cell", "Is Open");
		insertHeaderCell(row.insertCell(3), "standard-cell", "In Error");
		insertHeaderCell(row.insertCell(4), "standard-cell", "Rack");
		insertHeaderCell(row.insertCell(5), "standard-cell", "Barcode");
		insertHeaderCell(row.insertCell(6), "standard-cell", "RFID");
		insertHeaderCell(row.insertCell(7), "standard-cell", "Last Student");
		insertHeaderCell(row.insertCell(8), "note-cell", "Note");
    row.insertCell(9).outerHTML = "<th>Log Entry</th>";
		insertHeaderCell(row.insertCell(10), "timestamp-cell", "Log Timestamp");
}

function insertAltHeader(tableContainer) {
	var header = tableContainer.createTHead();
	var row = header.insertRow(0);
	insertHeaderCell(row.insertCell(0), "standard-cell", "Drawer Number");
	insertHeaderCell(row.insertCell(1), "standard-cell", "Checked Out");
	insertHeaderCell(row.insertCell(2), "standard-cell", "Is Open");
	insertHeaderCell(row.insertCell(3), "standard-cell", "In Error");
	insertHeaderCell(row.insertCell(4), "standard-cell", "Last Student");
	insertHeaderCell(row.insertCell(5), "note-cell", "Note");
	row.insertCell(6).outerHTML = "<th>Log Entry</th>";
	insertHeaderCell(row.insertCell(7), "timestamp-cell", "Log Timestamp");
}

function insertHeaderCell(cell, className, text) {
	cell.outerHTML = "<th class=\"" + className + "\">" + text + "</th>";
}
