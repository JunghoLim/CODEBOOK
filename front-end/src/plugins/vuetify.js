import Vue from 'vue';
//vuetify 기본 프레임워크
import Vuetify from 'vuetify/lib/framework'
//컬러 커스텀용 import
import colors from 'vuetify/lib/util/colors'
import '@/sass/overrides.sass'

Vue.use(Vuetify);

const theme = {
    //컬러 커스터마이징 부분
}

export default new Vuetify({
    theme: {
        themes: {
            // light: theme,
            // dark: theme
            // 테마 적용 부분
        }
    }
});
