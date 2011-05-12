package vdvil.server

class SongController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [songInstanceList: Song.list(params), songInstanceTotal: Song.count()]
    }

    def create = {
        def songInstance = new Song()
        songInstance.properties = params
        return [songInstance: songInstance]
    }

    def save = {
        def songInstance = new Song(params)
        if (songInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'song.label', default: 'Song'), songInstance.id])}"
            redirect(action: "show", id: songInstance.id)
        }
        else {
            render(view: "create", model: [songInstance: songInstance])
        }
    }

    def show = {
        def songInstance = Song.get(params.id)
        if (!songInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'song.label', default: 'Song'), params.id])}"
            redirect(action: "list")
        }
        else {
            [songInstance: songInstance]
        }
    }

    def edit = {
        def songInstance = Song.get(params.id)
        if (!songInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'song.label', default: 'Song'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [songInstance: songInstance]
        }
    }

    def update = {
        def songInstance = Song.get(params.id)
        if (songInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (songInstance.version > version) {
                    
                    songInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'song.label', default: 'Song')] as Object[], "Another user has updated this Song while you were editing")
                    render(view: "edit", model: [songInstance: songInstance])
                    return
                }
            }
            songInstance.properties = params
            if (!songInstance.hasErrors() && songInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'song.label', default: 'Song'), songInstance.id])}"
                redirect(action: "show", id: songInstance.id)
            }
            else {
                render(view: "edit", model: [songInstance: songInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'song.label', default: 'Song'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def songInstance = Song.get(params.id)
        if (songInstance) {
            try {
                songInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'song.label', default: 'Song'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'song.label', default: 'Song'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'song.label', default: 'Song'), params.id])}"
            redirect(action: "list")
        }
    }
}
