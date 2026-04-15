import axiosInstance from "../axios/client.js";

const earthquakesRepository = {
    findAll: async () => {
        const response = await axiosInstance.get('/earthquakes');
        return response.data;
    },
    fetchAndStore: async () => {
        const response = await axiosInstance.post('/earthquakes/fetch');
        return response.data;
    },
    filterByMagnitude: async (minMagnitude) => {
        const response = await axiosInstance.get('/earthquakes/filter/magnitude', {
            params: { minMagnitude }
        });
        return response.data;
    },
    filterByTime: async (timestamp) => {
        const response = await axiosInstance.get('/earthquakes/filter/time', {
            params: { timestamp }
        });
        return response.data;
    },
    deleteById: async (id) => {
        const response = await axiosInstance.delete(`/earthquakes/${id}`);
        return response.data;
    }
}

export default earthquakesRepository;
