<template>
    <div id="sidebar-wrapper">
        <div class="user">
            <div class="avatar">
                <img class="img-fluid rounded-circle" :src="user.avatar" />
            </div>

        </div>
        <div class="menu"></div>
    </div>  
</template>

<script>
export default {
    data() {
        return {
            user: {}
        }
    },
    mounted() {
        //this.user = window.User
    }
}
</script>

