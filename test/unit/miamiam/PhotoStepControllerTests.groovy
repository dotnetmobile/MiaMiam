/*  <MiaMiam: Web application for publishing recipes
    Copyright (C) <2014>  <Michel Petrovic> <email: dotnetmobile@gmail.com>

    Contributor(s): _____________________.

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package miamiam



import org.junit.*
import grails.test.mixin.*

@TestFor(PhotoStepController)
@Mock(PhotoStep)
class PhotoStepControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/photoStep/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.photoStepInstanceList.size() == 0
        assert model.photoStepInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.photoStepInstance != null
    }

    void testSave() {
        controller.save()

        assert model.photoStepInstance != null
        assert view == '/photoStep/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/photoStep/show/1'
        assert controller.flash.message != null
        assert PhotoStep.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/photoStep/list'

        populateValidParams(params)
        def photoStep = new PhotoStep(params)

        assert photoStep.save() != null

        params.id = photoStep.id

        def model = controller.show()

        assert model.photoStepInstance == photoStep
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/photoStep/list'

        populateValidParams(params)
        def photoStep = new PhotoStep(params)

        assert photoStep.save() != null

        params.id = photoStep.id

        def model = controller.edit()

        assert model.photoStepInstance == photoStep
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/photoStep/list'

        response.reset()

        populateValidParams(params)
        def photoStep = new PhotoStep(params)

        assert photoStep.save() != null

        // test invalid parameters in update
        params.id = photoStep.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/photoStep/edit"
        assert model.photoStepInstance != null

        photoStep.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/photoStep/show/$photoStep.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        photoStep.clearErrors()

        populateValidParams(params)
        params.id = photoStep.id
        params.version = -1
        controller.update()

        assert view == "/photoStep/edit"
        assert model.photoStepInstance != null
        assert model.photoStepInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/photoStep/list'

        response.reset()

        populateValidParams(params)
        def photoStep = new PhotoStep(params)

        assert photoStep.save() != null
        assert PhotoStep.count() == 1

        params.id = photoStep.id

        controller.delete()

        assert PhotoStep.count() == 0
        assert PhotoStep.get(photoStep.id) == null
        assert response.redirectedUrl == '/photoStep/list'
    }
}
