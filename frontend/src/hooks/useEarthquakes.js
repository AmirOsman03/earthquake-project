import {useState, useEffect, useCallback} from 'react';
import earthquakesRepository from "../repository/earthquakesRepository.js";

const initialState = {
    earthquakes: [],
    loading: true,
    error: null,
};

const UseEarthquakes = () => {
    const [state, setState] = useState(initialState);

    const fetchAndStoreEarthquakes = useCallback(() => {
        setState(prev => ({ ...prev, loading: true, error: null }));
        earthquakesRepository
            .fetchAndStore()
            .then((response) => {
                setState({
                    earthquakes: response || [],
                    loading: false,
                    error: null,
                })
            })
            .catch((error) => {
                console.log(error);
                setState(prev => ({ ...prev, loading: false, error: "Failed to sync earthquakes." }));
            })
    }, [])

    const fetchEarthquakes = useCallback(() => {
        setState(prev => ({ ...prev, loading: true, error: null }));
        earthquakesRepository
            .findAll()
            .then((response) => {
                setState({
                    earthquakes: response || [],
                    loading: false,
                    error: null,
                })
            })
            .catch((error) => {
                console.log(error);
                setState(prev => ({ ...prev, loading: false, error: "Failed to fetch earthquakes." }));
            })
    }, [])

    const filterByMagnitude = useCallback((minMagnitude) => {
        setState(prev => ({ ...prev, loading: true, error: null }));
        earthquakesRepository
            .filterByMagnitude(minMagnitude)
            .then((response) => {
                setState({
                    earthquakes: response || [],
                    loading: false,
                    error: null,
                })
            })
            .catch((error) => {
                console.log(error);
                setState(prev => ({ ...prev, loading: false, error: "Failed to filter by magnitude." }));
            })
    }, [])

    const filterByTime = useCallback((timestamp) => {
        setState(prev => ({ ...prev, loading: true, error: null }));
        earthquakesRepository
            .filterByTime(timestamp)
            .then((response) => {
                setState({
                    earthquakes: response || [],
                    loading: false,
                    error: null,
                })
            })
            .catch((error) => {
                console.log(error);
                setState(prev => ({ ...prev, loading: false, error: "Failed to filter by time." }));
            })
    }, [])

    const deleteById = useCallback((id) => {
        setState(prev => ({ ...prev, loading: true, error: null }));
        earthquakesRepository
            .deleteById(id)
            .then(() => {
                fetchEarthquakes();
                console.log('Earthquake deleted successfully');
            })
            .catch((error) => {
                console.log(error);
                setState(prev => ({ ...prev, loading: false, error: "Failed to delete earthquake." }));
            })
    }, [fetchEarthquakes])

    useEffect(() => {
        fetchEarthquakes();
    }, [fetchEarthquakes])

    return {
        ...state,
        fetchAndStoreEarthquakes,
        fetchEarthquakes,
        filterByMagnitude,
        filterByTime,
        deleteById,
    }
};

export default UseEarthquakes;
