/**
 * Faces Validator
 */
PrimeFaces.validator['crearCuenta.emailValidador'] = {

    pattern: /\S+@\S+/,

    validate: function(element, value) {
      
        if(!this.pattern.test(value)) {
            throw {
                summary: 'Validation Error',
                detail: value + ' is not a valid email.'
            }
        }
    }
};
