"use strict";

function Producer() {

  var self = this;

  self.init = function () {
    loadInfosystems();
  };

  function loadInfosystems() {
    $.getJSON('/systems.json', function (data) {
      self._createTableRows(data);
    });
  }

  self._createTableRows = function (data) {
    var templateRow = $('table #row-template').html();

    data.forEach(function (infosystem) {
      var row = $(templateRow);
      row.find('.name').text(infosystem.name);
      row.find('.short-name').text(infosystem.shortname);
      row.find('.documentation').text(infosystem.documentation);
      $('tbody').append(row);
    })
  }
}
