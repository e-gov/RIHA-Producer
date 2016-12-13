describe('Producer', function () {

  var data = [
    {
      "owner": "12345",
      "meta": {"URI": "/12345/lühinimi"},
      "documentation": "https://12345.com/lyhinimi",
      "name": "nimi",
      "shortname": "lühinimi",
      "status": {"timestamp": "2016-12-13T06:54:27.358"}
    },
    {
      "owner": "12345",
      "meta": {"URI": "/12345/short-name"},
      "documentation": "https://12345.com/short-name",
      "name": "name",
      "shortname": "short-name",
      "status": {"timestamp": "2016-12-13T13:16:16.037"}
    }
  ];

  it('fills table with info system data', function () {
    loadFixtures('index.html');

    new Producer()._createTableRows(data);

    var rows = $('tbody tr:not(.template-row)');

    expect(rows.length).toBe(2);
    expect($(rows[0]).find('.name').text()).toBe('nimi');
    expect($(rows[0]).find('.short-name').text()).toBe('lühinimi');
    expect($(rows[0]).find('.documentation').text()).toBe('https://12345.com/lyhinimi');
    expect($(rows[1]).find('.name').text()).toBe('name');
    expect($(rows[1]).find('.short-name').text()).toBe('short-name');
    expect($(rows[1]).find('.documentation').text()).toBe('https://12345.com/short-name');
  });
});