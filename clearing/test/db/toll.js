var expect = require('chai').expect;
var db = require('../../utils/db/setup')
var {Toll} = require('../../utils/db/model/entities')

describe('DB Toll Tests', function () {
  it('insert new toll', async function () {
    
    // 1. ARRANGE
    await Toll.sync({force: true})

    const mock = {
        toll_type: 'NORMAL',
        geolocation_latitude: 38.7054309,
        geolocation_longitude: -9.3407711,
        name: 'Portagem de Carcavelos'
    }

    await Toll.create(mock)

    // 2. ACT
    const curr = (await Toll.findAll())[0].dataValues

    // 3. ASSERT
    expect(curr.name).to.be.equal(mock.name)
    
  })
})