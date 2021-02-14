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

function getContent(location){
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
        presentData(this);
      }
  };
  xhttp.open("GET", "xml/" + location + ".xml", true);
  xhttp.send();
}

function presentData(xml) {
    var xmlDoc = xml.responseXML;

    var drawers = xmlDoc.getElementsByTagName("drawer");
    var tableContainer = document.getElementById("dataContainer");
    insertFullHeader(tableContainer);

    var tableBody = tableContainer.createTBody();

    for (i = 0; i < drawers.length; i++){
      var drawerId = drawers[i].childNodes[1].childNodes[0].nodeValue;
      var row = tableBody.insertRow(i);
      for (j = 1; j < 12; j++){
        if (j == 1 || j == 10){
          insertLinkToTable(j, row, drawers[i], drawerId);
        } else {
          insertDataToTable(j, row, drawers[i]);
        }
      }
    }
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

function insertLinkToTable(j, row, drawer, drawerId){
  var node = drawer.childNodes[j * 2 + 1].childNodes[0];
  if (node != null){
    row.insertCell(j - 1).innerHTML = "<a href=\"http://kiosk.aut.ac.nz/LockerBox/Drawers/Edit/" +
      drawerId + "\" >" + node.nodeValue + "</a>";
  } else {
    row.insertCell(j - 1).innerHTML = "";
  }
}

function insertDataToTable(j, row, drawer){
  var node = drawer.childNodes[j * 2 + 1].childNodes[0];
  if (node != null){
    row.insertCell(j - 1).innerHTML = node.nodeValue;
  } else {
    row.insertCell(j - 1).innerHTML = "";
  }
}
