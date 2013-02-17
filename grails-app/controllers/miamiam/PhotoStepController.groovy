package miamiam

import org.springframework.dao.DataIntegrityViolationException

class PhotoStepController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
		params.sort = params.sort ?: "name"
		params.order = params.order ?: "asc"

        [photoStepInstanceList: PhotoStep.list(params), photoStepInstanceTotal: PhotoStep.count()]
    }

    def create() {
        [photoStepInstance: new PhotoStep(params)]
    }

    def save() {
        def photoStepInstance = new PhotoStep(params)
        if (!photoStepInstance.save(flush: true)) {
            render(view: "create", model: [photoStepInstance: photoStepInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'photoStep.label', default: 'PhotoStep'), photoStepInstance.id])
        redirect(action: "show", id: photoStepInstance.id)
    }

    def show(Long id) {
        def photoStepInstance = PhotoStep.get(id)
        if (!photoStepInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'photoStep.label', default: 'PhotoStep'), id])
            redirect(action: "list")
            return
        }

        [photoStepInstance: photoStepInstance]
    }

    def edit(Long id) {
        def photoStepInstance = PhotoStep.get(id)
        if (!photoStepInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'photoStep.label', default: 'PhotoStep'), id])
            redirect(action: "list")
            return
        }

        [photoStepInstance: photoStepInstance]
    }

    def update(Long id, Long version) {
        def photoStepInstance = PhotoStep.get(id)
        if (!photoStepInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'photoStep.label', default: 'PhotoStep'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (photoStepInstance.version > version) {
                photoStepInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'photoStep.label', default: 'PhotoStep')] as Object[],
                          "Another user has updated this PhotoStep while you were editing")
                render(view: "edit", model: [photoStepInstance: photoStepInstance])
                return
            }
        }

        photoStepInstance.properties = params

        if (!photoStepInstance.save(flush: true)) {
            render(view: "edit", model: [photoStepInstance: photoStepInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'photoStep.label', default: 'PhotoStep'), photoStepInstance.id])
        redirect(action: "show", id: photoStepInstance.id)
    }

    def delete(Long id) {
        def photoStepInstance = PhotoStep.get(id)
        if (!photoStepInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'photoStep.label', default: 'PhotoStep'), id])
            redirect(action: "list")
            return
        }

        try {
            photoStepInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'photoStep.label', default: 'PhotoStep'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'photoStep.label', default: 'PhotoStep'), id])
            redirect(action: "show", id: id)
        }
    }
	
	def viewImage(Long id) {
		def photo = PhotoStep.get(id)
		byte[] image = photo.photo
		response.outputStream << image
	}
}
