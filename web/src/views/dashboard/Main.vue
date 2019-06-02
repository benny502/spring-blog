<template>
    <el-container>
        <el-aside>
            <Sidebar></Sidebar>
        </el-aside>
        <el-main>
            <router-view></router-view>
        </el-main>
    </el-container>
</template>

<script>

import Sidebar from './components/Sidebar'
export default {
    components: {
        Sidebar
    }
}
</script>

