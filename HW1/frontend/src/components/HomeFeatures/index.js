import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import FormControl from '@mui/material/FormControl';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import Paper from '@mui/material/Paper';
import Select from '@mui/material/Select';
import Typography from '@mui/material/Typography';

const App = () => {
    const [selectedOption1, setSelectedOption1] = useState('');
    const [selectedOption2, setSelectedOption2] = useState('');
    const [options1, setOptions1] = useState([]);
    const [options2, setOptions2] = useState([]);
    const [jsonObject, setJsonObject] = useState({
    });
  
    useEffect(() => {
        async function fetchOptions1() {
            try {
                const response = await axios.get('http://localhost:8080/api/v1/countries');
                setOptions1(response.data);
            } catch (error) {
                console.error('Error fetching options for the first dropdown:', error);
            }
        }
        fetchOptions1();
    }, []);
  
    const handleSelect1 = async (event) => {
        setSelectedOption1(event.target.value);
        try {
            const response = await axios.get(`http://localhost:8080/api/v1/stations/${event.target.value}`);
            setOptions2(response.data);
        } catch (error) {
            console.error('Error fetching options for the second dropdown:', error);
        }
    };
  
    const handleSelect2 = (event) => {
        setSelectedOption2(event.target.value);
    };
  
    const handleClick = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/api/v1/air/${selectedOption2}`);
            setJsonObject(response.data);
        } catch (error) {
            console.error('Error fetching data:', error);
        }
      };

    return (
        <Box sx={{ display: 'flex', alignItems: 'center', justifyContent: 'center', minHeight: 'calc(100vh - 150px)', width: '100%', bgcolor: '#f5f5f5'}}>
            <Box sx={{ display: 'flex', flexDirection: 'row', justifyContent: 'space-between', alignItems: 'flex-start', p: 4, bgcolor: 'background.paper', borderRadius: '4px', boxShadow: 2, width: '80%'}}>
                <Box sx={{paddingTop: '5%' }}>
                    <Typography variant="h4" sx={{ mb: 2 }}>Air Quality Checker</Typography>
                    <FormControl fullWidth sx={{ mt: 4, width: '100%'}}>
                        <InputLabel id="select-label-1">Select a country</InputLabel>
                        <Select labelId="select-label-1" value={selectedOption1} onChange={handleSelect1} label="Select a country" sx={{ minWidth: 200 }}>
                            <MenuItem value=""><em>Countries</em> </MenuItem>
                            {options1.map((option) => (
                                <MenuItem key={option} value={option}>{option}</MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                    <FormControl fullWidth sx={{ mt: 2, width: '100%' }}>
                        <InputLabel id="select-label-2">Select a station</InputLabel>
                        <Select labelId="select-label-2" value={selectedOption2} onChange={handleSelect2} label="Select a station" sx={{ minWidth: 200 }}>
                            <MenuItem value=""><em>Stations</em></MenuItem>
                            {Object.entries(options2).map(([key, value]) => (
                                <MenuItem key={key} value={key}>
                                {value}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                    <Button variant="contained" color="primary" onClick={handleClick} sx={{ mt: 4 }}>Request Air Quality</Button>
                </Box>
                <Box>
                    <Typography variant="h5" align="center" sx={{ mb: 3 }}><strong>Station's Response</strong></Typography>
                    <Paper sx={{ p: 2, mt: 1, height: '470px', width: '600px', overflow: 'auto', whiteSpace: 'pre-wrap', bgcolor: 'background.default', borderRadius: '4px', bgcolor: '#f5f5f5', boxShadow: 2 }}>
                        <Typography>
                            <strong>Location</strong><br />
                            {'\t'}<strong>Code</strong>{'\t'}{'\t'}{jsonObject.location?.code}<br />
                            {'\t'}<strong>Name</strong>{'\t'}{'\t'}{jsonObject.location?.name}<br />
                            {'\t'}<strong>Country</strong>{'\t'}{'\t'}{jsonObject.location?.country}<br />
                            {'\t'}<strong>Geolocation</strong>{'\t'}{jsonObject.location?.geolocation?.join(', ')}
                            <br /><br />
                            <strong>Air Quality</strong><br />
                            {'\t'}<strong>Air Quality String</strong>{'\t'}{'\t'}{jsonObject.airQuality?.airQualityString}<br />
                            {'\t'}<strong>Air Quality Index</strong>{'\t'}{'\t'}{jsonObject.airQuality?.airQualityIndex}<br />
                            {'\t'}<strong>PM2.5</strong>{'\t'}{'\t'}{'\t'}{'\t'}{jsonObject.airQuality?.pm25}<br />
                            {'\t'}<strong>PM10</strong>{'\t'}{'\t'}{'\t'}{'\t'}{jsonObject.airQuality?.pm10}<br />
                            {'\t'}<strong>NO2</strong>{'\t'}{'\t'}{'\t'}{'\t'}{jsonObject.airQuality?.no2}<br />
                            {'\t'}<strong>O3</strong>{'\t'}{'\t'}{'\t'}{'\t'}{'\t'}{jsonObject.airQuality?.o3}<br />
                            {'\t'}<strong>Water Gauge</strong>{'\t'}{'\t'}{'\t'}{jsonObject.airQuality?.waterGauge}<br />
                            {'\t'}<strong>Dominent Pollutant</strong>{'\t'}{jsonObject.airQuality?.dominentPolutent}
                            <br /><br />
                            <strong>Day</strong>{'\t'}{'\t'}{'\t'} {jsonObject.day}<br />
                            <strong>Timestamp</strong>{'\t'} {jsonObject.timestamp}
                        </Typography>
                    </Paper>
                </Box>
            </Box>
        </Box>
    );
};

export default App;
