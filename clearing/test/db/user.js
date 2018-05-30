var expect = require('chai').expect;
var db = require('../../utils/db/setup')
var {User} = require('../../utils/db/model/entities')

describe('DB User Tests', function () {
  it('insert new user', async function () {
    
    // 1. ARRANGE
    await User.sync({force: true})

    const mock = {
      nif: 1231837821,
      name: 'John',
      password: 'pw',
      state: 'GOOD'
    }

    await User.create(mock)

    // 2. ACT
    const curr = (await User.findAll())[0].dataValues

    // 3. ASSERT
    expect(curr.nif).to.be.equal(mock.nif)

  })
})